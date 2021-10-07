package com.disco.shoppingCart.user.dao;

import com.disco.shoppingCart.user.dto.User;
import com.disco.shoppingCart.user.response.UserResponse;

public interface UserDao {
    UserResponse createUser(User user);

    UserResponse readUser(String userId);

    UserResponse updateUser(String userId, User user);

    User readUserAsUser(String userId);

    UserResponse removeUser(String userId);
}
