package com.disco.shoppingcart.user.service;

import com.disco.shoppingcart.user.model.User;
import com.disco.shoppingcart.user.model.UserResponse;

public interface UserService {

    UserResponse createUser(User user);

    UserResponse readUser(String userId);

    UserResponse updateUser(String userId, User user);

    UserResponse deleteUser(String userId);

}
