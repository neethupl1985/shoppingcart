package com.disco.shoppingCart;

public enum FURNITURE {
    CHAIR("CH"),
    COUCH("CO"),
    DESK("DK"),
    TABLE("TB");
    private final String value;

    FURNITURE(String value) {
        this.value = value;
    }
}
