package com.disco.shoppingCart.inventory.dao;

import com.disco.shoppingCart.inventory.dto.Inventory;
import com.disco.shoppingCart.inventory.model.InventoryResponse;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface InventoryDao {
    InventoryResponse addItemsToInventory(List<Inventory> items);

    InventoryResponse readItemFromInventory(String itemId);

    Inventory readAnItem(String itemId);

    InventoryResponse updateItemPriceToInventory(String itemId, Inventory inventory);

    InventoryResponse updateItemCountToInventory(String itemId, String updateType, Inventory inventory);

    InventoryResponse deleteItems(String itemId) throws DataAccessException;
}
