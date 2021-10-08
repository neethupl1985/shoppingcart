package com.disco.shoppingcart.userCart.dao.impl;

import com.disco.shoppingcart.exception.ShoppingCartException;
import com.disco.shoppingcart.inventory.dao.InventoryDao;
import com.disco.shoppingcart.inventory.model.Inventory;
import com.disco.shoppingcart.user.dao.UserDao;
import com.disco.shoppingcart.userCart.dao.UserCartDao;
import com.disco.shoppingcart.userCart.dto.UserCart;
import com.disco.shoppingcart.userCart.response.UserCartResponse;
import com.disco.shoppingcart.userCart.mapper.UserCartMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Slf4j
@Repository
public class UserCartDaoImpl implements UserCartDao {

    private final JdbcTemplate jdbcTemplate;

    private final InventoryDao inventoryDao;

    private final UserDao userDao;

    @Autowired
    public UserCartDaoImpl(JdbcTemplate jdbcTemplate, InventoryDao inventoryDao, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.inventoryDao = inventoryDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserCartResponse addItemTomUserCart(UserCart userCart) {
        /*
        When items are added to user cart make sure that many number of items are deleted from the inventory table
        */
        try {
            Inventory inventory = inventoryDao.readAnItem(userCart.getInventoryId());

            String sqlInventory = "UPDATE TESTDB.INVENTORY SET COUNT = ?  WHERE inventoryId = ?";
            jdbcTemplate.update(
                    sqlInventory,
                    (int) Math.max(0, inventory.getCount() - userCart.getItemCount()),
                    userCart.getInventoryId());

            String sqlUserCart = "INSERT INTO TESTDB.USERCART VALUES (?,?,?,?,?)";
            Object[] values = new Object[]{
                    userCart.getUserId(),
                    userCart.getInventoryId(),
                    userCart.getItemType(),
                    userCart.getItemCount(),
                    (inventory.getPrice() * userCart.getItemCount())
            };
            jdbcTemplate.update(sqlUserCart, values);
            return UserCartResponse.builder()
                    .status(HttpStatus.OK)
                    .requestBody("Items Added to cart Successfully!")
                    .build();
        } catch (DataAccessException exception) {
            throw new ShoppingCartException("DataAccessException", "Failed to add to usercart table",
                    HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public UserCartResponse readItemsFromUserCart(String userId) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String sql;
            List<UserCart> userCart;
            if (StringUtils.isEmpty(userId)) {
                sql = "SELECT * FROM TESTDB.USERCART";
                userCart = jdbcTemplate.query(sql, new UserCartMapper());

            } else {
                sql = "SELECT * FROM TESTDB.USERCART WHERE userId = ?";
                userCart = jdbcTemplate.query(sql, new UserCartMapper(), userId);
            }
            return UserCartResponse.builder().status(HttpStatus.OK).
                    requestBody(objectMapper.writeValueAsString(userCart))
                    .build();
        } catch (JsonProcessingException exception) {
            throw new ShoppingCartException("JsonProcessingException", "Failed to convert result of read " +
                    "to Json! ",
                    HttpStatus.UNPROCESSABLE_ENTITY.value());
        }
    }


    @Override
    public UserCartResponse deleteAllItemsFromCart(String userId) {
        //Add all items back to the inventory from usercart for a delete
        try {
            List<UserCart> userCarts = retrieveUsersCart(userId);

            userCarts.forEach(cart -> {
                Inventory inventory = inventoryDao.readAnItem(cart.getInventoryId());
                updateItemCountInInventory(cart.getItemCount() + inventory.getCount(), cart.getInventoryId());
            });

            String sqlUpdate = "DELETE FROM  TESTDB.USERCART  WHERE userId = ? ";

            jdbcTemplate.update(sqlUpdate, userId);
            return UserCartResponse.builder().status(HttpStatus.OK).requestBody("Users cart removed Successfully!")
                    .build();

        } catch (DataAccessException exception) {
            throw new ShoppingCartException("DataAccessException", "Failed to remove items from user's cart",
                    HttpStatus.BAD_REQUEST.value());
        }
    }


    @Override
    @Transactional
    public UserCartResponse updateItemInUserCart(String operation, UserCart userCart) {
        try {
        /*
        When items are removed from user cart make sure that many number of items are added back to inventory table
         */
            Inventory inventory = inventoryDao.readAnItem(userCart.getInventoryId());

            //get all the items user has added to his/her cart
            List<UserCart> userCarts = retrieveUsersCart(userCart.getUserId());

            int count;

            count = userCarts.stream()
                    .filter(cart -> cart.getInventoryId().equals(userCart.getInventoryId()))
                    .map(UserCart::getItemCount).findFirst().orElse(0);

            String sqlUserCart = "UPDATE  TESTDB.USERCART SET ITEMCOUNT = ? AND ITEMPRICE =? " +
                    "WHERE USERID = ? AND INVENTORYID = ?";
            String msg;
            double itemPrice;
            int itemCount;
            if (operation.equals("A")) {
                itemCount = count + userCart.getItemCount();
                itemPrice = itemCount * inventory.getPrice();

                updateItemCountInInventory(Math.max(0, inventory.getCount() - userCart.getItemCount())
                        , userCart.getInventoryId());
                jdbcTemplate.update(sqlUserCart, itemCount, itemPrice, userCart.getUserId()
                        , userCart.getInventoryId());
                jdbcTemplate.query("SELECT * FROM TESTDB.USERCART WHERE userId = ?", new UserCartMapper(),  userCart.getUserId());
                msg = "Item added Successfully!";
            } else {
                itemCount = count - userCart.getItemCount();
                itemPrice = itemCount * inventory.getPrice();
                updateItemCountInInventory(inventory.getCount() + userCart.getItemCount()
                        , userCart.getInventoryId());
                jdbcTemplate.update(sqlUserCart, Math.max(0, itemCount), itemPrice, userCart.getUserId()
                        , userCart.getInventoryId());
                msg = "Item removed Successfully!";

            }
            return UserCartResponse.builder().status(HttpStatus.OK).requestBody(msg).build();
        } catch (DataAccessException exception) {
            throw new ShoppingCartException("DataAccessException", "Failed to update item in user cart",
                    HttpStatus.BAD_REQUEST.value());
        }

    }

    @Override
    public List<UserCart> retrieveUsersCart(String userId) {
        try {
            String sql = "SELECT * FROM TESTDB.USERCART WHERE userId = ?";
            return jdbcTemplate.query(sql, new UserCartMapper(), userId);
        } catch (DataAccessException exception) {
            throw new ShoppingCartException("DataAccessException", "Failed to read from usercart table",
                    HttpStatus.BAD_REQUEST.value());
        }
    }

    public void updateItemCountInInventory(long count, String inventoryId) {
        String sqlInventory = "UPDATE TESTDB.INVENTORY SET COUNT = ? WHERE inventoryId = ?";
        jdbcTemplate.update(sqlInventory, count, inventoryId);
    }
}