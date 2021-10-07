package com.disco.shoppingCart.user.dao.impl;

import com.disco.shoppingCart.user.dao.UserDao;
import com.disco.shoppingCart.user.dto.User;
import com.disco.shoppingCart.user.response.UserResponse;
import com.disco.shoppingCart.utils.UserMapper;
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
        try {

            String sql = "INSERT INTO TESTDB.USER VALUES (?,?, ?)";
            Object[] values = new Object[]{user.getUserId(), user.getFirstName(),
                    user.getLastName()
            };
            jdbcTemplate.update(sql, values);
            return UserResponse.builder().status(HttpStatus.OK).requestBody("User Added Successfully!").build();

        } catch (DataAccessException exception) {
            log.info(exception.getMessage());
            return UserResponse.builder().status(HttpStatus.BAD_REQUEST).requestBody("Failed to Added user! " +
                    exception.getMessage()).build();
        }
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
                Object[] id = new Object[]{userId};
                userList = jdbcTemplate.query(sql, new UserMapper(),id);

            }
            ObjectMapper objectMapper = new ObjectMapper();
            log.info("{}", objectMapper.writeValueAsString(userList));
            return UserResponse.builder().status(HttpStatus.OK).
                    requestBody(objectMapper.writeValueAsString(userList))
                    .build();
        } catch (JsonProcessingException exception) {
            return UserResponse.builder().status(HttpStatus.OK).requestBody("Failed to read user")
                    .build();
        }
    }

    @Override
    public User readUserAsUser(String userId) {
        String sql = "SELECT * FROM TESTDB.USER WHERE userId = ?";
        Object[] id = new Object[]{userId};
        return jdbcTemplate.queryForObject(sql, new UserMapper(),id);
    }


    @Override
    public UserResponse updateUser(String userId, User user) {
        String sql = "UPDATE TESTDB.USER SET firstName = ? where lastName = ?";
        try {
            jdbcTemplate.update(sql, user.getFirstName(), user.getLastName());
            return UserResponse.builder().status(HttpStatus.OK).requestBody("Users updated Successfully!").build();
        } catch (DataAccessException exception) {
            return UserResponse.builder().status(HttpStatus.OK).requestBody("Failed to update user")
                    .build();
        }
    }

    @Override
    public UserResponse removeUser(String userId) {

        String sql = "DELETE FROM TESTDB.USER WHERE userId = ?";
        Object[] id = new Object[]{userId};
        try {
            jdbcTemplate.update(sql, id);
            return UserResponse.builder().status(HttpStatus.OK).requestBody("User deleted Successfully!").build();

        } catch (DataAccessException exception) {
            return UserResponse.builder().status(HttpStatus.OK).requestBody("Failed to delete User").build();
        }

    }
}
