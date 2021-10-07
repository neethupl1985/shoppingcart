package com.disco.shoppingCart.userCart.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCart {
    private String userId;
    private String inventoryId;
    private String itemType;
    private int itemCount;
    private double itemPrice;

}
