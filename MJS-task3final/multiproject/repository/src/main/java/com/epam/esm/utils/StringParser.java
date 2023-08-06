package com.epam.esm.utils;

public class StringParser {

    private StringParser() {
    }

    public static String getOrderBy(String order) {
        if (order != null && order.contains(":")) {
            return order.substring(0, order.indexOf(':'));
        } else if (order != null && !order.contains(":")) {
            return order;
        }
        return "id";
    }

    public static String getOrderType(String order) {
        if (order != null && order.contains(":")) {
            return order.substring(order.indexOf(':') + 1);
        } else {
            return "asc";
        }
    }
}
