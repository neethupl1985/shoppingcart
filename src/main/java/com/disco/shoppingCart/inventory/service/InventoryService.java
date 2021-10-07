package com.disco.shoppingCart.inventory.service;

import com.disco.shoppingCart.inventory.dto.Inventory;
import com.disco.shoppingCart.inventory.model.InventoryResponse;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface InventoryService {

    InventoryResponse addItemsToInventory(List<Inventory> items);

    InventoryResponse readItemFromInventory(String itemId);

    InventoryResponse updateItemCountToInventory(String itemId, String updateType, Inventory inventory);

    InventoryResponse updateItemPriceToInventory(String itemId, Inventory inventory);

    InventoryResponse deleteItemsFromInventory(String itemId) throws DataAccessException;

}
