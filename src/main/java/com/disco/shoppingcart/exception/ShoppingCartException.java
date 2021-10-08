package com.disco.shoppingcart.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@Slf4j
public class ShoppingCartException extends RuntimeException {
    protected String title;
    protected String detail;
    protected int statusCode;

    public ShoppingCartException(final String title, final String detail, final int statusCode) {
        super(title);
        this.title = title;
        this.detail = detail;
        this.statusCode = statusCode;
    }
}
