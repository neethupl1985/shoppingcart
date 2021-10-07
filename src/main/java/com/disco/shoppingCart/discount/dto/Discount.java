package com.disco.shoppingCart.discount.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Discount {
    private String discountCode;
    private String discountItems;
    private int value;
    private int percentage;
}
