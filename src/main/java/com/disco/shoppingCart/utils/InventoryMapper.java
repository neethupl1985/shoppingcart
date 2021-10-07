package com.disco.shoppingCart.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.disco.shoppingCart.inventory.dto.Inventory;
import org.springframework.jdbc.core.RowMapper;

public class InventoryMapper implements RowMapper<Inventory> {
    public Inventory mapRow(ResultSet rs, int rowNum) throws SQLException {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(rs.getString("inventoryId"));
        inventory.setItem(rs.getString("item"));
        inventory.setItemType(rs.getString("itemType"));
        inventory.setCount(rs.getLong("count"));
        inventory.setPrice(rs.getDouble("price"));
        return inventory;
    }
}