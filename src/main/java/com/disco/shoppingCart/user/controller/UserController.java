package com.disco.shoppingCart.user.controller;

import com.disco.shoppingCart.user.dto.User;
import com.disco.shoppingCart.user.response.UserResponse;
import com.disco.shoppingCart.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * This method is used to add user
     *
     * @param user the user json object
     * @return response object
     */
    @PostMapping(path = "/createUser")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        UserResponse userResponse = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    /**
     * This method is used to read all users
     *
     * @return response object
     */
    @GetMapping(path = "/getAllUsers")
    public ResponseEntity<Object> getAllUsers() {
        UserResponse userResponse = userService.readUser("");
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    /**
     * This method is used to read single user
     *
     * @return response object
     */
    @GetMapping(path = "/getAUser/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable final String userId) {
        UserResponse userResponse = userService.readUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    /**
     * Update Item price
     */
    @PutMapping(path = "/updateUser/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable final String userId,
                                             @RequestBody final User user) {

        UserResponse userResponse = userService.updateUser(userId, user);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }


    /**
     * Delete Users
     */
    @DeleteMapping(path = "/deleteUser/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable final String userId) {
        UserResponse userResponse = userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
}