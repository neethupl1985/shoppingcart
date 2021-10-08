package com.disco.shoppingcart.inventory.dao.impl;

import com.disco.shoppingcart.exception.ShoppingCartException;
import com.disco.shoppingcart.inventory.mapper.InventoryMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.disco.shoppingcart.inventory.dao.InventoryDao;
import com.disco.shoppingcart.inventory.model.Inventory;
import com.disco.shoppingcart.inventory.model.InventoryResponse;

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

            return InventoryResponse
                    .builder()
                    .status(HttpStatus.CREATED)
                    .requestBody("All items Added Successfully!").build();

        } catch (DataAccessException exception) {
            throw new ShoppingCartException("DataAccessException", "Failed to insert items to inventory",
                    HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public InventoryResponse readItemFromInventory(String itemId) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Inventory> inventoryList;
            String sql;
            if (StringUtils.isEmpty(itemId)) {
                sql = "SELECT * FROM TESTDB.INVENTORY";
                inventoryList = jdbcTemplate.query(sql, new InventoryMapper());
            } else {
                sql = "SELECT * FROM TESTDB.INVENTORY WHERE inventoryId = ?";
                inventoryList = jdbcTemplate.query(sql, new InventoryMapper(), itemId);
            }
            return InventoryResponse.builder().status(HttpStatus.OK).
                    requestBody(objectMapper.writeValueAsString(inventoryList))
                    .build();

        } catch (JsonProcessingException exception) {
            throw new ShoppingCartException("JsonProcessingException", "Failed to convert result of read " +
                    "to Json! ",
                    HttpStatus.UNPROCESSABLE_ENTITY.value());
        }
    }


    @Override
    public Inventory readAnItem(String itemId) {
        try {
            String sql = "SELECT * FROM TESTDB.INVENTORY WHERE inventoryId = ?";
            return jdbcTemplate.queryForObject(sql, new InventoryMapper(), itemId);
        } catch (DataAccessException exception) {
            throw new ShoppingCartException("DataAccessException", "Failed to read an item from inventory table",
                    HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public InventoryResponse updateItemPriceToInventory(String itemId, Inventory inventory) {
        String sql = "UPDATE TESTDB.INVENTORY SET price = ? where inventoryId = ?";
        jdbcTemplate.update(sql, inventory.getPrice(), inventory.getInventoryId());
        return InventoryResponse.builder().status(HttpStatus.NO_CONTENT).requestBody("Items updated Successfully!").build();
    }

    @Override
    public InventoryResponse updateItemCountToInventory(String itemId, String updateType, Inventory inventory) {
        String sql;
        try {
            sql = "UPDATE TESTDB.INVENTORY SET count = ? where inventoryId = ?";
            String countSql = "SELECT COUNT FROM TESTDB.INVENTORY where inventoryId = ?";

            jdbcTemplate.query("select * from TESTDB.INVENTORY where inventoryId = ?", new InventoryMapper(), itemId);

            readAnItem(itemId);
            long count = jdbcTemplate.queryForObject(countSql, Long.class, itemId);
            long newCount;

            String action = "A";
            if (updateType.equals(action)) {
                //add new price to the inventory table
                newCount = count + inventory.getCount();
            } else {
                //ignore negative values and set 0 when items are removed from the inventory
                newCount = Math.max(0, inventory.getCount() - count);
            }
            jdbcTemplate.update(sql, newCount, inventory.getInventoryId());
            return InventoryResponse.builder().status(HttpStatus.NO_CONTENT).requestBody("Items updated Successfully!").build();
        } catch (DataAccessException exception) {
            throw new ShoppingCartException("DataAccessException", "Failed to update item count in  inventory table for " +
                    "inventoryId " + inventory.getInventoryId(),
                    HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public InventoryResponse deleteItem(String itemId) {
        try {
            String sql = "DELETE FROM TESTDB.INVENTORY WHERE inventoryId = ?";

            jdbcTemplate.update(sql, itemId);
            return InventoryResponse.builder()
                    .status(HttpStatus.NO_CONTENT)
                    .requestBody("Items deleted Successfully!").build();
        } catch (DataAccessException exception) {
            throw new ShoppingCartException("DataAccessException", "Failed to delete item from  inventory table for " +
                    "inventoryId " + itemId,
                    HttpStatus.BAD_REQUEST.value());
        }
    }
}
