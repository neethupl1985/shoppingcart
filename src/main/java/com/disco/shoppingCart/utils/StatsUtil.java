package com.disco.shoppingCart.utils;

public enum StatsUtil {
    OPERATION_STATUS,
    UTC_DATETIME;
    public String toString() {
        return name().toLowerCase();
    }
}
