package com.disco.shoppingcart.userCart.dao;

import com.disco.shoppingcart.userCart.model.Summary;
import com.disco.shoppingcart.userCart.response.UserCartResponse;
import com.disco.shoppingcart.userCart.dto.UserCart;

import java.util.List;


public interface UserCartDao {
    UserCartResponse addItemTomUserCart(UserCart userCart);

    UserCartResponse readItemsFromUserCart(String userId);

    List<UserCart> retrieveUsersCart(String userId);

    UserCartResponse updateItemInUserCart(String operation, UserCart userCart);

    UserCartResponse deleteAllItemsFromCart(String userId);

}