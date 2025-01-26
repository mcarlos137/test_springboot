package com.silkrivercapital.springboot.controllers.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.silkrivercapital.springboot.entities.Trade;
import com.silkrivercapital.springboot.services.TradeService;

@RestController
@RequestMapping("/trades")
public class TradeRESTController {

    @Autowired
    private TradeService service;

    @PostMapping("/create")
    ResponseEntity<Trade> create(@RequestBody Trade trade) {
        Trade persistedTrade = service.create(trade);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(persistedTrade.getId())
                .toUri();
        return ResponseEntity //
                .created(uri) //
                .body(persistedTrade);
    }

    @GetMapping(path = "/findAllByBotId", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Trade>> findAllByBotId(@RequestParam("botId") String botId) {
        List<Trade> trades = service.findAllByBotId(botId);
        return new ResponseEntity<>(trades, HttpStatus.OK);
    }

    @GetMapping(path = "/findAllByBaseAssetAndQuoteAsset", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Trade>> findAllByBaseAssetAndQuoteAsset(@RequestParam("baseAsset") String baseAsset,
            @RequestParam("quoteAsset") String quoteAsset) {
        List<Trade> trades = service.findAllByBaseAssetAndQuoteAsset(baseAsset, quoteAsset);
        return new ResponseEntity<>(trades, HttpStatus.OK);
    }

}