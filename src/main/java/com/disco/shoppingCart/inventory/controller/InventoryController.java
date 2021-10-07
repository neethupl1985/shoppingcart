package com.disco.shoppingCart.inventory.controller;


import com.disco.shoppingCart.inventory.dto.Inventory;
import com.disco.shoppingCart.inventory.service.InventoryService;
import com.disco.shoppingCart.inventory.model.InventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Create Items
     */
    @PostMapping(path = "/createItems")
    public ResponseEntity<InventoryResponse> createItems(@RequestBody final List<Inventory> items) {
        InventoryResponse inventoryResponse = inventoryService.addItemsToInventory(items);
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryResponse);
    }

    /**
     * Read Items
     */
    @GetMapping(path = "/getAllItems")
    public ResponseEntity<InventoryResponse> getAllItems() {
        InventoryResponse inventoryResponse = inventoryService.readItemFromInventory("");
        return ResponseEntity.status(HttpStatus.OK).body(inventoryResponse);
    }


    /**
     * Read Items
     */
    @GetMapping(path = "/getItem/{itemId}")
    public ResponseEntity<InventoryResponse> getItem(@PathVariable final String itemId) {
        InventoryResponse inventoryResponse = inventoryService.readItemFromInventory(itemId);
        return ResponseEntity.status(HttpStatus.OK).body(inventoryResponse);
    }


    /**
     * Update Item count
     */
    @PutMapping(path = "/updateItemCount/{updateType}/{itemId}")
    public ResponseEntity<InventoryResponse> updateItemCount(@PathVariable final String itemId,
                                                             @PathVariable final String updateType,
                                                             @RequestBody final Inventory inventory) {
        InventoryResponse inventoryResponse = inventoryService.updateItemCountToInventory(itemId, updateType, inventory);
        return ResponseEntity.status(HttpStatus.OK).body(inventoryResponse);
    }

    /**
     * Update Item price
     */
    @PutMapping(path = "/updateItemPrice/{itemId}")
    public ResponseEntity<InventoryResponse> updateItemPrice(@PathVariable final String itemId,
                                                             @RequestBody final Inventory inventory) {


        InventoryResponse inventoryResponse = inventoryService.updateItemPriceToInventory(itemId, inventory);
        return ResponseEntity.status(HttpStatus.OK).body(inventoryResponse);
    }

    /**
     * Delete Items
     */
    @DeleteMapping(path = "/deleteItem/{itemId}")
    public ResponseEntity<Object> deleteItem(@PathVariable final String itemId) {
        InventoryResponse inventoryResponse = inventoryService.deleteItemsFromInventory(itemId);
        return ResponseEntity.status(HttpStatus.OK).body(inventoryResponse);
    }
}
