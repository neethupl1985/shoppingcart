package com.disco.shoppingCart.inventory.dto;


import lombok.*;

@Getter
@Setter
public class Inventory {
    private String inventoryId;
    private String item;
    private String itemType;
    private Long count;
    private Double price;
}
