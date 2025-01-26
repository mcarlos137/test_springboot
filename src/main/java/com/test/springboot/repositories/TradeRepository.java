package com.silkrivercapital.springboot.repositories;

import org.springframework.data.repository.Repository;

import com.silkrivercapital.springboot.entities.Trade;

public interface TradeRepository extends Repository<Trade, Long>, TradeRepositoryCustom {

}
