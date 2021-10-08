package com.disco.shoppingcart.inventory.service.impl;


import com.disco.shoppingcart.inventory.dao.InventoryDao;
import com.disco.shoppingcart.inventory.model.Inventory;
import com.disco.shoppingcart.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.disco.shoppingcart.inventory.model.InventoryResponse;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryDao inventoryDao;

    @Autowired
    public InventoryServiceImpl(InventoryDao inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    @Override
    public InventoryResponse addItemsToInventory(List<Inventory> items) {
        return inventoryDao.addItemsToInventory(items);
    }

    @Override
    public InventoryResponse readItemFromInventory(String itemId) {
        return inventoryDao.readItemFromInventory(itemId);
    }

    @Override
    public InventoryResponse updateItemCountToInventory(String itemId, String updateType, Inventory inventory) {
        return inventoryDao.updateItemCountToInventory(itemId, updateType, inventory);
    }

    @Override
    public InventoryResponse updateItemPriceToInventory(String itemId, Inventory inventory)  {
        return inventoryDao.updateItemPriceToInventory(itemId, inventory);
    }

    @Override
    public InventoryResponse deleteItemFromInventory(String itemId) {
        return inventoryDao.deleteItem(itemId);
    }
}
