package com.test.springboot.enums;

public enum RedisStreamKey {

    ORDER_BOOK("order_book");

    private final String label;

    RedisStreamKey(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
