package com.silkrivercapital.springboot.enums;

public enum RabbitmqQueue {

    ORDER_BOOK_BINANCE_ETH_USDT("order_book.binance.ETH-USDT"),
    ORDER_BOOK_BINANCE_SOL_USDT("order_book.binance.SOL-USDT");

    private final String label;

    RabbitmqQueue(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
