package com.disco.shoppingCart.user.service.impl;

import com.disco.shoppingCart.user.dao.UserDao;
import com.disco.shoppingCart.user.dto.User;
import com.disco.shoppingCart.user.response.UserResponse;
import com.disco.shoppingCart.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserResponse createUser(User user) {
        return userDao.createUser(user);
    }

    @Override
    public UserResponse readUser(String userId) {
        return userDao.readUser(userId);
    }

    @Override
    public UserResponse updateUser(String userId, User user) {
        return userDao.updateUser(userId, user);
    }

    @Override
    public UserResponse deleteUser(String userId) {
        return userDao.removeUser(userId);
    }
}
