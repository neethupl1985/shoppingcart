package com.disco.shoppingcart.userCart.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Summary {

    private String userId;
    private String firstName;
    private String lastName;
    private double totalPriceBeforeDiscount;
    private double discountsApplied;
    private double finalPrice;
}
