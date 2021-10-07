package com.disco.shoppingCart.inventory.dao.impl;

import com.disco.shoppingCart.inventory.dto.Inventory;
import com.disco.shoppingCart.inventory.dao.InventoryDao;
import com.disco.shoppingCart.inventory.model.InventoryResponse;
import com.disco.shoppingCart.utils.InventoryMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class InventoryDaoImpl implements InventoryDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InventoryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public InventoryResponse addItemsToInventory(List<Inventory> items) {
        try {

            String sql = "INSERT INTO TESTDB.INVENTORY VALUES (?,?, ?,?,?)";
            List<Object[]> inventoryListValues = new ArrayList<>();
            for (Inventory inventory : items) {
                Object[] values = new Object[]{inventory.getInventoryId(),
                        inventory.getItem(),
                        inventory.getItemType(),
                        inventory.getCount().intValue(),
                        inventory.getPrice()
                };
                inventoryListValues.add(values);
            }
            jdbcTemplate.batchUpdate(sql, inventoryListValues);
            return InventoryResponse.builder().status(HttpStatus.OK).requestBody("Items Added Successfully!").build();

        } catch (DataAccessException exception) {
            log.info(exception.getMessage());
            return InventoryResponse.builder().status(HttpStatus.BAD_REQUEST).requestBody("Failed to Added item! " +
                    exception.getMessage()).build();
        }
    }

    @Override
    public InventoryResponse readItemFromInventory(String itemId) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String sql;
            List<Inventory> inventoryList;
            if (StringUtils.isEmpty(itemId)) {
                sql = "SELECT * FROM TESTDB.INVENTORY";
                inventoryList = jdbcTemplate.query(sql, new InventoryMapper());

            } else {
                sql = "SELECT * FROM TESTDB.INVENTORY WHERE inventoryId = ?";
                Object[] id = new Object[]{itemId};
                inventoryList = jdbcTemplate.query(sql, new InventoryMapper(), id);
            }

            return InventoryResponse.builder().status(HttpStatus.OK).
                    requestBody(objectMapper.writeValueAsString(inventoryList))
                    .build();
        } catch (JsonProcessingException exception) {
            return InventoryResponse.builder().status(HttpStatus.OK).requestBody("Failed to get items")
                    .build();
        }
    }


    @Override
    public Inventory readAnItem(String itemId) {
        String sql = "SELECT * FROM TESTDB.INVENTORY WHERE inventoryId = ?";
        Object[] id = new Object[]{itemId};
        return jdbcTemplate.queryForObject(sql, new InventoryMapper(), id);
    }

    @Override
    public InventoryResponse updateItemPriceToInventory(String itemId, Inventory inventory) {
        String sql = "UPDATE TESTDB.INVENTORY SET price = ? where inventoryId = ?";
        try {
            jdbcTemplate.update(sql, inventory.getPrice(), inventory.getInventoryId());
            return InventoryResponse.builder().status(HttpStatus.OK).requestBody("Items updated Successfully!").build();
        } catch (DataAccessException exception) {
            return InventoryResponse.builder().status(HttpStatus.OK).requestBody("Failed to update item")
                    .build();
        }
    }

    @Override
    public InventoryResponse updateItemCountToInventory(String itemId, String updateType, Inventory inventory) {
        String sql;
        sql = "UPDATE TESTDB.INVENTORY SET count = ? where inventoryId = ?";

        try {
            String value = "SELECT COUNT FROM TESTDB.INVENTORY where inventoryId = ?";
            double count = jdbcTemplate.queryForObject(value, Integer.class, itemId);
            double newCount;

            String ADD = "A";
            if (updateType.equals(ADD)) {
                //add new price to the inventory table
                newCount = count + inventory.getCount();
            } else {
                //ignore negative values and set 0 when items are removed from the inventory 
                newCount = Math.max(0, inventory.getCount() - count);
            }
            jdbcTemplate.update(sql, newCount, inventory.getInventoryId());
            return InventoryResponse.builder().status(HttpStatus.OK).requestBody("Items updated Successfully!").build();
        } catch (DataAccessException exception) {
            return InventoryResponse.builder().status(HttpStatus.OK).requestBody("Failed to update item")
                    .build();
        }
    }

    @Override
    public InventoryResponse deleteItems(String itemId) throws DataAccessException {
        String sql = "DELETE FROM TESTDB.INVENTORY WHERE inventoryId = ?";
        Object[] id = new Object[]{itemId};
        try {
            jdbcTemplate.update(sql, id);
            return InventoryResponse.builder().status(HttpStatus.OK).requestBody("Items deleted Successfully!").build();

        } catch (DataAccessException exception) {
            return InventoryResponse.builder().status(HttpStatus.OK).requestBody("Failed to delete item").build();
        }
    }
}
