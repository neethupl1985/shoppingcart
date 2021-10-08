package com.disco.shoppingcart.user;


import com.disco.shoppingcart.user.controller.UserController;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // force name ordering
public class UserControllerTest {
    @Resource
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

    }

    /*
    Test to create a user in User table
     */
    @Test
    public void createUserTest() throws Exception {
        File resource = new ClassPathResource("user/addUser.json").getFile();
        String userAddFile = new String(Files.readAllBytes(resource.toPath()));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/user/createUser/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userAddFile);

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.handler().handlerType(UserController.class))
                .andExpect(MockMvcResultMatchers.handler().methodName("createUser"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
    }

    /*
    Test to delete a user from User table
     */
    @Test
    public void deleteUserTest() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .delete("/user/deleteUser/{userId}", "INV100")
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.handler().handlerType(UserController.class))
                .andExpect(MockMvcResultMatchers.handler().methodName("deleteUser"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
    }

    /*
    Test to get all users from User table
     */
    @Test
    public void getAllUsersTest() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/user/getAllUsers");

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.handler().handlerType(UserController.class))
                .andExpect(MockMvcResultMatchers.handler().methodName("getAllUsers"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
    }

    /*
    Test to update a user in User table
     */
    @Test
    public void updateUserTest() throws Exception {
        File resource = new ClassPathResource("user/updateUser.json").getFile();
        String inventoryUpdateFile = new String(Files.readAllBytes(resource.toPath()));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("/user/updateUser/{userId}", "USR1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inventoryUpdateFile);

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.handler().handlerType(UserController.class))
                .andExpect(MockMvcResultMatchers.handler().methodName("updateUser"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
    }


}
