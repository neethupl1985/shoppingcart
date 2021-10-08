package com.disco.shoppingcart.user.dao;

import com.disco.shoppingcart.user.model.User;
import com.disco.shoppingcart.user.model.UserResponse;

public interface UserDao {
    UserResponse createUser(User user);

    UserResponse readUser(String userId);

    UserResponse updateUser(String userId, User user);

    User readUserAsUser(String userId);

    UserResponse removeUser(String userId);
}
