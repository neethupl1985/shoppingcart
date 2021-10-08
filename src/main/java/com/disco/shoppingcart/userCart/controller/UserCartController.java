package com.disco.shoppingcart.userCart.controller;

import com.disco.shoppingcart.userCart.model.Summary;
import com.disco.shoppingcart.userCart.response.UserCartResponse;
import com.disco.shoppingcart.utils.MdcUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.disco.shoppingcart.userCart.dto.UserCart;
import com.disco.shoppingcart.userCart.service.UserCartService;


@RestController
@Slf4j
@RequestMapping(path = "/cart")
public class UserCartController {

    private final UserCartService cartService;

    @Autowired
    public UserCartController(UserCartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Add an item to user's cart
     */
    @PostMapping(path = "/addToCart")
    public ResponseEntity<UserCartResponse> addItemToUserCart(@RequestBody final UserCart userCart) {
        UserCartResponse userCartResponse = cartService.addItemTomUserCart(userCart);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCartResponse);
    }

    /**
     * Read an item from user's cart
     */
    @GetMapping(path = "/getUserCart/{userId}")
    public ResponseEntity<UserCartResponse> readItemFromUserCart(@PathVariable final String userId) {
        UserCartResponse userCartResponse = cartService.readItemsFromUserCart(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userCartResponse);
    }

    /**
     * Delete an Item from Cart
     */
    @DeleteMapping(path = "/deleteAllItemsFromCart/{userId}")
    public ResponseEntity<UserCartResponse> deleteAllItemsFromCart(@PathVariable final String userId) {
        UserCartResponse userCartResponse = cartService.deleteAllItemsFromCart(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userCartResponse);
    }

    /**
     * Update an Item count in Cart
     */
    @PutMapping(path = "/updateItemInUserCart/{operation}")
    public ResponseEntity<UserCartResponse> updateItemInUserCart(@PathVariable final String operation,
                                                               @RequestBody final UserCart userCart) {
        UserCartResponse userCartResponse = cartService.updateItemInUserCart(operation,userCart);
        return ResponseEntity.status(HttpStatus.OK).body(userCartResponse);
    }


    /**
     * Read an item from user's cart
     */
    @GetMapping(path = "/summary/{userId}")
    public ResponseEntity<Summary> summary(@PathVariable final String userId) {
        Summary summary = cartService.summary(userId);
        return ResponseEntity.status(HttpStatus.OK).body(summary);
    }
}
