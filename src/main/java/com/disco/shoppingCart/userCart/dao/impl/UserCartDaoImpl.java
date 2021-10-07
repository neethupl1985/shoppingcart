package com.disco.shoppingCart.userCart.dao.impl;

import com.disco.shoppingCart.FURNITURE;
import com.disco.shoppingCart.inventory.dao.InventoryDao;
import com.disco.shoppingCart.inventory.dto.Inventory;
import com.disco.shoppingCart.user.dao.UserDao;
import com.disco.shoppingCart.user.dto.User;
import com.disco.shoppingCart.userCart.Summary;
import com.disco.shoppingCart.userCart.dao.UserCartDao;
import com.disco.shoppingCart.userCart.dto.UserCart;
import com.disco.shoppingCart.userCart.response.UserCartResponse;
import com.disco.shoppingCart.utils.UserCartMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static java.util.Comparator.comparingDouble;

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
    public UserCartResponse addItemTomUserCart(UserCart userCart) {
        try {
            /*
            When items are added to user cart make sure that many number of items are deleted from the inventory table
             */
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
            log.info(exception.getMessage());
            return UserCartResponse.builder().status(HttpStatus.BAD_REQUEST).requestBody("Failed to Add items to cart! " +
                    exception.getMessage()).build();
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
                Object[] id = new Object[]{userId};
                userCart = jdbcTemplate.query(sql, new UserCartMapper(), id);

            }
            return UserCartResponse.builder().status(HttpStatus.OK).
                    requestBody(objectMapper.writeValueAsString(userCart))
                    .build();
        } catch (JsonProcessingException exception) {
            return UserCartResponse.builder().status(HttpStatus.OK).requestBody("Failed to get items")
                    .build();
        }
    }

    @Override
    public List<UserCart> readSingleUserFromUserCart(String userId) {
        String sql = "SELECT * FROM TESTDB.USERCART WHERE userId = ?";
        Object[] id = new Object[]{userId};
        return jdbcTemplate.query(sql, new UserCartMapper(), id);
    }

    @Override
    public UserCartResponse updateItemFromUserCart(UserCart userCart, String operation) {
        /*
        When items are removed from user cart make sure that many number of items are added back to inventory table
         */
        Inventory inventory = inventoryDao.readAnItem(userCart.getInventoryId());

        String sqlInventory = "UPDATE TESTDB.INVENTORY SET COUNT = ? WHERE inventoryId = ?";

        jdbcTemplate.update(sqlInventory, inventory.getCount() + userCart.getItemCount(),
                userCart.getInventoryId());


        List<UserCart> userCarts = readSingleUserFromUserCart(userCart.getUserId());

        int count = 0;
        for (UserCart cart : userCarts) {
            if (cart.getInventoryId().equals(userCart.getInventoryId())) {
                count = cart.getItemCount();
            }
        }

        String sqlUpdate = "UPDATE  TESTDB.USERCART SET COUNT = ? WHERE userId = ? AND INVENTORYID = ?";
        if (operation.equals("A")) {
            jdbcTemplate.update(sqlUpdate, count + userCart.getItemCount(), userCart.getUserId());
        } else {
            jdbcTemplate.update(sqlUpdate, Math.max(0, count - userCart.getItemCount()), userCart.getUserId());

        }
        return UserCartResponse.builder().status(HttpStatus.OK).requestBody("Item removed Successfully!").build();


    }

    @Override
    public UserCartResponse removeAUserCart(String userId) {
        try {
            String sqlUpdate = "DELETE FROM  TESTDB.USERCART  WHERE userId = ? ";

            jdbcTemplate.update(sqlUpdate, userId);
            return UserCartResponse.builder().status(HttpStatus.OK).requestBody("Users cart removed Successfully!")
                    .build();

        } catch (DataAccessException exception) {
            log.info(exception.getMessage());
            return UserCartResponse.builder().status(HttpStatus.BAD_REQUEST).requestBody("Failed to remove users cart  " +
                    exception.getMessage()).build();
        }
    }

    @Override
    public Summary summary(String userId) {

        double totalPrice = 0;
        List<UserCart> userCart = readSingleUserFromUserCart(userId);
        SortedSet<Double> maxDiscount = new TreeSet<>();
        Map<FURNITURE, Integer> discountMap = new HashMap<>();

        User user = userDao.readUserAsUser(userId);

        for (UserCart cart : userCart) {
            int itemCount = cart.getItemCount();
            totalPrice += cart.getItemPrice();
            if (cart.getItemType().equals("CH")) {
                discountMap.put(FURNITURE.CHAIR, itemCount);
                if (itemCount >= 4) {
                    maxDiscount.add(20.0);
                }
            } else if (cart.getItemType().equals(FURNITURE.COUCH.toString())) {
                discountMap.put(FURNITURE.COUCH, itemCount);
            } else if (cart.getItemType().equals(FURNITURE.TABLE.toString())) {
                discountMap.put(FURNITURE.TABLE, itemCount);
            } else if (cart.getItemType().equals(FURNITURE.DESK.toString())) {
                discountMap.put(FURNITURE.DESK, itemCount);
            }
        }
        if (!discountMap.containsValue(0) && discountMap.size() == 4) {
            Integer minCountOfAllItems =
                    Collections.min(discountMap.entrySet(), comparingDouble(Map.Entry::getValue)).getValue();
            maxDiscount.add(minCountOfAllItems * 17.0);
        }
        if (totalPrice > 1000) {
            maxDiscount.add(15.0);
        }
        return Summary.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .discountsApplied(maxDiscount.last())
                .totalPriceBeforeDiscount(totalPrice)
                .finalPrice(totalPrice-(totalPrice * (maxDiscount.last() / 100))).build();


    }
}