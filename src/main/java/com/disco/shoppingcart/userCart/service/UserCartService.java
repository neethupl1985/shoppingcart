package com.disco.shoppingcart.userCart.service;

import com.disco.shoppingcart.inventory.model.Inventory;
import com.disco.shoppingcart.userCart.dto.UserCart;
import com.disco.shoppingcart.userCart.model.Summary;
import com.disco.shoppingcart.userCart.response.UserCartResponse;

public interface UserCartService {

    UserCartResponse addItemTomUserCart(UserCart userCart);

    UserCartResponse readItemsFromUserCart(String userId);

    UserCartResponse updateItemInUserCart(String userId, UserCart userCart);

    UserCartResponse deleteAllItemsFromCart(String userId);

    Summary summary(String userId);
}
