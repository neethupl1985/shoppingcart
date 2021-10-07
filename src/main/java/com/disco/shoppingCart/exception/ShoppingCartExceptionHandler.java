package com.disco.shoppingCart.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@Slf4j
@AllArgsConstructor
public class ShoppingCartExceptionHandler {

    protected String title;
    protected String type;
    protected Object detail;
    protected int statusCode;

}
