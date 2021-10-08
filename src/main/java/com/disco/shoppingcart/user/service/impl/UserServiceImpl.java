package com.disco.shoppingcart.user.service.impl;

import com.disco.shoppingcart.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.disco.shoppingcart.user.dao.UserDao;
import com.disco.shoppingcart.user.model.User;
import com.disco.shoppingcart.user.model.UserResponse;

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
