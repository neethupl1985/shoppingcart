package com.disco.shoppingcart.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@Slf4j
@AllArgsConstructor
public class ShoppingCartExceptionHandler {
    private String title;
    private String type;
    private String detail;
    private int statusCode;
}
