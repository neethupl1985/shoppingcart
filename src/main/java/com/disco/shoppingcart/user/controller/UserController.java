package com.disco.shoppingcart.user.controller;

import com.disco.shoppingcart.user.service.UserService;
import com.disco.shoppingcart.utils.MdcUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.disco.shoppingcart.user.model.User;
import com.disco.shoppingcart.user.model.UserResponse;


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
    public ResponseEntity<UserResponse> createUser(@RequestBody User user) {

        UserResponse userResponse = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    /**
     * This method is used to read all users from User table
     * @return response object
     */
    @GetMapping(path = "/getAllUsers")
    public ResponseEntity<UserResponse> getAllUsers() {
        UserResponse userResponse = userService.readUser("");
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    /**
     * This method is used to read single user from user table
     * @param userId the userId of the user
     * @return response object
     */
    @GetMapping(path = "/getAUser/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable final String userId) {
        UserResponse userResponse = userService.readUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    /**
     * Update Item price
     * @param userId the userId of the user
     *  @param user the user json object
     */
    @PutMapping(path = "/updateUser/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable final String userId,
                                             @RequestBody final User user) {
        UserResponse userResponse = userService.updateUser(userId, user);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }


    /**
     * Delete Users
     */
    @DeleteMapping(path = "/deleteUser/{userId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable final String userId) {
        UserResponse userResponse = userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
}