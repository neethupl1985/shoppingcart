package com.disco.shoppingCart.userCart;

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
