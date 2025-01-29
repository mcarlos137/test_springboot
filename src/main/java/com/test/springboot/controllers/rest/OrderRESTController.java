package com.test.springboot.controllers.rest;

import java.net.URI;
import java.util.List;
import java.util.UUID;

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

import com.test.springboot.entities.Order;
import com.test.springboot.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderRESTController {

    @Autowired
    private OrderService service;

    @GetMapping(path = "/findAllByBotId", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Order>> findAllByBotId(@RequestParam("botId") String botId) {
        List<Order> orders = service.findAllByBotId(botId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping(path = "/findAllByBaseAssetAndQuoteAsset", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Order>> findAllByBaseAssetAndQuoteAsset(@RequestParam("baseAsset") String baseAsset,
            @RequestParam("quoteAsset") String quoteAsset) {
        List<Order> orders = service.findAllByBaseAssetAndQuoteAsset(baseAsset, quoteAsset);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/create")
    ResponseEntity<Order> create(@RequestBody Order order) {
        Order persistedOrder = service.create(order);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(persistedOrder.getId())
                .toUri();
        return ResponseEntity //
                .created(uri) //
                .body(persistedOrder);
    }

    @PostMapping("/cancel")
    ResponseEntity<Order> cancel(@RequestBody String id) {
        System.out.println("-----> " + id);
        Order mergedOrder = service.cancel(UUID.fromString(id));
        return ResponseEntity
                .ok()
                .body(mergedOrder); 
    }

}