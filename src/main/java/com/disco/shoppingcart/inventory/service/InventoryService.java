package com.disco.shoppingcart.inventory.service;

import com.disco.shoppingcart.inventory.model.Inventory;
import com.disco.shoppingcart.inventory.model.InventoryResponse;

import java.util.List;

public interface InventoryService {

    InventoryResponse addItemsToInventory(List<Inventory> items);

    InventoryResponse readItemFromInventory(String itemId);

    InventoryResponse updateItemCountToInventory(String itemId, String updateType, Inventory inventory);

    InventoryResponse updateItemPriceToInventory(String itemId, Inventory inventory);

    InventoryResponse deleteItemFromInventory(String itemId) ;

}
