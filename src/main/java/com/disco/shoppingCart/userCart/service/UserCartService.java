package com.disco.shoppingCart.userCart.service;

import com.disco.shoppingCart.inventory.dto.Inventory;
import com.disco.shoppingCart.userCart.Summary;
import com.disco.shoppingCart.userCart.dto.UserCart;
import com.disco.shoppingCart.userCart.response.UserCartResponse;

public interface UserCartService {

    UserCartResponse addItemTomUserCart(UserCart userCart);

    UserCartResponse readItemsFromUserCart(String userId);

    UserCartResponse updateItemFromUserCart(Inventory item);

    UserCartResponse removeAUserCart(String userId);

    Summary summary(String userId);
}
