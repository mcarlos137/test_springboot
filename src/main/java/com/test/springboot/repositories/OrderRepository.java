package com.silkrivercapital.springboot.repositories;

import java.util.UUID;

import org.springframework.data.repository.Repository;

import com.silkrivercapital.springboot.entities.Order;

public interface OrderRepository extends Repository<Order, UUID>, OrderRepositoryCustom {

}
