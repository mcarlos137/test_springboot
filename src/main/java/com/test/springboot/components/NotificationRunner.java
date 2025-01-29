package com.test.springboot.components;

import java.sql.*;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NotificationRunner implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        Connection lConn = dataSource.getConnection();
        Listener listener = new Listener(lConn);
        listener.start();
    }
}

class Listener extends Thread {

    private Connection conn;
    private org.postgresql.PGConnection pgconn;

    Listener(Connection conn) throws SQLException {
        this.conn = conn;
        this.pgconn = conn.unwrap(org.postgresql.PGConnection.class);
        Statement stmt = conn.createStatement();
        stmt.execute("LISTEN notify_order_book_ethusdt");
        stmt.close();
    }

    public void run() {
        try {
            while (true) {
                org.postgresql.PGNotification notifications[] = pgconn.getNotifications();
                if (notifications != null) {
                    Statement stmt = conn.createStatement();
                    for (org.postgresql.PGNotification notification : notifications) {
                        ResultSet resultSet = stmt.executeQuery("SELECT * FROM public." + notification.getParameter() + ";");
                        while(resultSet.next()){
                            System.out.println("-------------- " + resultSet.getString("id"));
                        }                        
                    }
                    stmt.close();
                }
                Thread.sleep(500);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}
