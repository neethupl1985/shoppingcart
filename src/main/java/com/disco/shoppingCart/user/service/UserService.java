package com.disco.shoppingCart.user.service;

import com.disco.shoppingCart.user.dto.User;
import com.disco.shoppingCart.user.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    UserResponse createUser(User user);

    UserResponse readUser(String userId);

    UserResponse updateUser(String userId, User user);

    UserResponse deleteUser(String userId);

}
