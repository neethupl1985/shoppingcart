package com.disco.shoppingcart.integration.usercart;

import com.disco.shoppingcart.inventory.controller.InventoryController;
import com.disco.shoppingcart.user.controller.UserController;
import com.disco.shoppingcart.userCart.controller.UserCartController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class UserCartControllerITest {

    @Resource
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /*
    This integration test will run the whole flow of adding items to inventory , adding users , adding items to users cart and then
    finally  calculating the discount .
     */
    @Test
    public void addItemToUserCartTest() throws Exception {
        File inventoryResource = new ClassPathResource("inventory/addInventory.json").getFile();
        String inventoryAddFile = new String(Files.readAllBytes(inventoryResource.toPath()));

        MockHttpServletRequestBuilder builderInventory = MockMvcRequestBuilders
                .post("/inventory/createItems/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inventoryAddFile);

        this.mockMvc.perform(builderInventory)
                .andExpect(MockMvcResultMatchers.handler().handlerType(InventoryController.class))
                .andExpect(MockMvcResultMatchers.handler().methodName("createItems"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();


        File userResource = new ClassPathResource("user/addUser.json").getFile();
        String userAddFile = new String(Files.readAllBytes(userResource.toPath()));

        MockHttpServletRequestBuilder builderUser = MockMvcRequestBuilders
                .post("/user/createUser/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userAddFile);

        this.mockMvc.perform(builderUser)
                .andExpect(MockMvcResultMatchers.handler().handlerType(UserController.class))
                .andExpect(MockMvcResultMatchers.handler().methodName("createUser"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();


        File userCartResource = new ClassPathResource("usercart/addToUserCart.json").getFile();
        String userCartAddFile = new String(Files.readAllBytes(userCartResource.toPath()));

        MockHttpServletRequestBuilder builderUserCart = MockMvcRequestBuilders
                .post("/cart/addToCart/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userCartAddFile);

        this.mockMvc.perform(builderUserCart)
                .andExpect(MockMvcResultMatchers.handler().handlerType(UserCartController.class))
                .andExpect(MockMvcResultMatchers.handler().methodName("addItemToUserCart"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        MockHttpServletRequestBuilder buildSummary = MockMvcRequestBuilders
                .get("/cart/summary/{userId}", "USR1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userCartAddFile);

        this.mockMvc.perform(buildSummary)
                .andExpect(MockMvcResultMatchers.handler().handlerType(UserCartController.class))
                .andExpect(MockMvcResultMatchers.handler().methodName("summary"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
}
