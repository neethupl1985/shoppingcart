package com.disco.shoppingCart.utils;

import com.disco.shoppingCart.userCart.dto.UserCart;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCartMapper implements RowMapper<UserCart> {
    public UserCart mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserCart userCart = new UserCart();
        userCart.setUserId(rs.getString("userId"));
        userCart.setInventoryId(rs.getString("inventoryId"));
        userCart.setItemType(rs.getString("itemType"));
        userCart.setItemCount(rs.getInt("itemCount"));
        userCart.setItemPrice(rs.getDouble("itemPrice"));
        return userCart;
    }
}