package com.disco.shoppingcart.user.dao.impl;

import com.disco.shoppingcart.exception.ShoppingCartException;
import com.disco.shoppingcart.user.dao.UserDao;
import com.disco.shoppingcart.user.model.User;
import com.disco.shoppingcart.user.model.UserResponse;
import com.disco.shoppingcart.user.mapper.UserMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserResponse createUser(User user) {
        String sql = "INSERT INTO TESTDB.USER VALUES (?,?, ?)";
        Object[] values = new Object[]{user.getUserId(), user.getFirstName(),
                user.getLastName()
        };
        jdbcTemplate.update(sql, values);
        return UserResponse.builder().status(HttpStatus.OK).requestBody("User Added Successfully!").build();
    }

    @Override
    public UserResponse readUser(String userId) {
        try {
            String sql;
            List<User> userList;
            if (StringUtils.isEmpty(userId)) {
                sql = "SELECT * FROM TESTDB.USER";
                userList = jdbcTemplate.query(sql, new UserMapper());

            } else {
                sql = "SELECT * FROM TESTDB.USER WHERE userId = ?";
                userList = jdbcTemplate.query(sql, new UserMapper(), userId);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            log.info("{}", objectMapper.writeValueAsString(userList));
            return UserResponse.builder().status(HttpStatus.OK).
                    requestBody(objectMapper.writeValueAsString(userList))
                    .build();
        } catch (JsonProcessingException exception) {
            throw new ShoppingCartException("JsonProcessingException", "Failed to convert result of read " +
                    "to Json! ",
                    HttpStatus.UNPROCESSABLE_ENTITY.value());
        }
    }

    @Override
    public User readUserAsUser(String userId) {
        try {
            String sql = "SELECT * FROM TESTDB.USER WHERE userId = ?";
            return jdbcTemplate.queryForObject(sql, new UserMapper(), userId);
        } catch (DataAccessException exception) {
            throw new ShoppingCartException("DataAccessException", "Failed to read from user table",
                    HttpStatus.BAD_REQUEST.value());
        }

    }

    @Override
    public UserResponse updateUser(String userId, User user) {
        try {
            String sql = "UPDATE TESTDB.USER SET firstName = ? where lastName = ?";
            jdbcTemplate.update(sql, user.getFirstName(), user.getLastName());
            return UserResponse.builder().status(HttpStatus.OK).requestBody("Users updated Successfully!").build();
        } catch (DataAccessException exception) {
            throw new ShoppingCartException("DataAccessException", "Failed to update user :" + userId + "table",
                    HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public UserResponse removeUser(String userId) {
        try {
            String sql = "DELETE FROM TESTDB.USER WHERE userId = ?";
            jdbcTemplate.update(sql, userId);
            return UserResponse.builder().status(HttpStatus.OK).requestBody("User deleted Successfully!").build();
        } catch (DataAccessException exception) {
            throw new ShoppingCartException("DataAccessException", "Failed to remove user :" + userId + "table",
                    HttpStatus.BAD_REQUEST.value());
        }
    }
}
