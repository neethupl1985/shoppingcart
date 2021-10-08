package com.disco.shoppingcart.inventory.dao;

import com.disco.shoppingcart.inventory.model.Inventory;
import com.disco.shoppingcart.inventory.model.InventoryResponse;

import java.util.List;

public interface InventoryDao {
    InventoryResponse addItemsToInventory(List<Inventory> items);

    InventoryResponse readItemFromInventory(String itemId);

    Inventory readAnItem(String itemId);

    InventoryResponse updateItemPriceToInventory(String itemId, Inventory inventory);

    InventoryResponse updateItemCountToInventory(String itemId, String updateType, Inventory inventory);

    InventoryResponse deleteItem(String itemId);
}
