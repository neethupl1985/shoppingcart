package com.disco.shoppingCart.userCart.controller;

import com.disco.shoppingCart.userCart.Summary;
import com.disco.shoppingCart.userCart.dto.UserCart;
import com.disco.shoppingCart.userCart.response.UserCartResponse;
import com.disco.shoppingCart.userCart.service.UserCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
     * Read an item from user's cart
     */
    @DeleteMapping(path = "/deleteItemFromUserCart/{userId}")
    public ResponseEntity<UserCartResponse> removeAUserCart(@PathVariable final String userId) {

        UserCartResponse userCartResponse = cartService.removeAUserCart(userId);
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
