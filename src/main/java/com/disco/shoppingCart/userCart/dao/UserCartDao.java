package com.disco.shoppingCart.userCart.dao;

import com.disco.shoppingCart.userCart.Summary;
import com.disco.shoppingCart.userCart.dto.UserCart;
import com.disco.shoppingCart.userCart.response.UserCartResponse;

import java.util.List;


public interface UserCartDao {
    UserCartResponse addItemTomUserCart(UserCart userCart);

    UserCartResponse readItemsFromUserCart(String userId);

    List<UserCart> readSingleUserFromUserCart(String userId);

    UserCartResponse updateItemFromUserCart(UserCart userCart, String operation);

    UserCartResponse removeAUserCart(String userId);

    Summary summary(String userId);

}
