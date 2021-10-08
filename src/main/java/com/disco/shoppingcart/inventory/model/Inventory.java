package com.disco.shoppingcart.inventory.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inventory {
    private String inventoryId;
    private String item;
    private String itemType;
    private Long count;
    private Double price;
}
