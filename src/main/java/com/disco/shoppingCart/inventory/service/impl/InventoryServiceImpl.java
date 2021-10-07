package com.disco.shoppingCart.inventory.service.impl;


import com.disco.shoppingCart.inventory.dao.InventoryDao;
import com.disco.shoppingCart.inventory.dto.Inventory;
import com.disco.shoppingCart.inventory.service.InventoryService;
import com.disco.shoppingCart.inventory.model.InventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public InventoryResponse updateItemPriceToInventory(String itemId, Inventory inventory) {
        return inventoryDao.updateItemPriceToInventory(itemId, inventory);
    }

    @Override
    public InventoryResponse deleteItemsFromInventory(String itemId) {
        return inventoryDao.deleteItems(itemId);
    }

}
