package com.silkrivercapital.springboot.enums;

public enum RedisChannelTopic {

    ORDER_BOOK_TRANSFORMATION_BASIC("order_book_transformation_basic"),;

    private final String label;

    RedisChannelTopic(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
