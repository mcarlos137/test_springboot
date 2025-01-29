package com.test.springboot.repositories;

import org.springframework.data.repository.Repository;

import com.test.springboot.entities.Trade;

public interface TradeRepository extends Repository<Trade, Long>, TradeRepositoryCustom {

}
