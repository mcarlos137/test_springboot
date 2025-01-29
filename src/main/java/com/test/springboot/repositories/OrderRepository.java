package com.test.springboot.repositories;

import java.util.UUID;

import org.springframework.data.repository.Repository;

import com.test.springboot.entities.Order;

public interface OrderRepository extends Repository<Order, UUID>, OrderRepositoryCustom {

}
