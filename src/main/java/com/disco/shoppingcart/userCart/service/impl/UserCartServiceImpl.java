package com.disco.shoppingcart.userCart.service.impl;

import com.disco.shoppingcart.user.dao.UserDao;
import com.disco.shoppingcart.userCart.dao.UserCartDao;
import com.disco.shoppingcart.userCart.dto.UserCart;
import com.disco.shoppingcart.userCart.model.Summary;
import com.disco.shoppingcart.userCart.response.UserCartResponse;
import com.disco.shoppingcart.userCart.service.UserCartService;
import com.disco.shoppingcart.utils.DiscountCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserCartServiceImpl implements UserCartService {

    private final UserCartDao userCartDao;
    private final UserDao userDao;

    @Autowired
    public UserCartServiceImpl(UserCartDao userCartDao, UserDao userDao) {
        this.userCartDao = userCartDao;
        this.userDao = userDao;
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
    public UserCartResponse updateItemInUserCart(String operation,UserCart userCart) {
        return userCartDao.updateItemInUserCart(operation,userCart);
    }

    @Override
    public UserCartResponse deleteAllItemsFromCart(String userId) {
        return userCartDao.deleteAllItemsFromCart(userId);
    }

    @Override
    public Summary summary(String userId) {
        return DiscountCalculator.summary(userCartDao.retrieveUsersCart(userId), userDao.readUserAsUser(userId));
    }
}
