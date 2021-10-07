package com.disco.shoppingCart.userCart.service.impl;

import com.disco.shoppingCart.inventory.dto.Inventory;
import com.disco.shoppingCart.userCart.Summary;
import com.disco.shoppingCart.userCart.dao.UserCartDao;
import com.disco.shoppingCart.userCart.dto.UserCart;
import com.disco.shoppingCart.userCart.response.UserCartResponse;
import com.disco.shoppingCart.userCart.service.UserCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserCartServiceImpl implements UserCartService {

    private final UserCartDao userCartDao;

    @Autowired
    public UserCartServiceImpl(UserCartDao userCartDao) {
        this.userCartDao = userCartDao;
    }

    @Override
    public UserCartResponse addItemTomUserCart(UserCart userCart) {
        return userCartDao.addItemTomUserCart(userCart);
    }

    @Override
    public UserCartResponse readItemsFromUserCart(String userId) {
        return userCartDao.readItemsFromUserCart(userId);
    }

    @Override
    public UserCartResponse updateItemFromUserCart(Inventory item) {
        return null;
    }

    @Override
    public UserCartResponse removeAUserCart(String userId) {
        return userCartDao.removeAUserCart(userId);
    }

    @Override
    public Summary summary(String userId) {
        return userCartDao.summary(userId);
    }
}
