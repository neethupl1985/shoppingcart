package com.disco.shoppingCart.inventory;


import com.disco.shoppingCart.inventory.controller.InventoryController;
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
public class InventoryControllerTest {
    @Resource
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

    }

    @Test
    public void createItemsTest() throws Exception {
        File resource = new ClassPathResource("inventory/addInventory.json").getFile();
        String inventoryAddFile = new String(Files.readAllBytes(resource.toPath()));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/inventory/createItems/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inventoryAddFile);

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.handler().handlerType(InventoryController.class))
                .andExpect(MockMvcResultMatchers.handler().methodName("createItems"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void deleteItemTest() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .delete("/inventory/deleteItem/{itemId}", 101)
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.handler().handlerType(InventoryController.class))
                .andExpect(MockMvcResultMatchers.handler().methodName("deleteItem"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
    }


    @Test
    public void getAllItemsTest() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/inventory/getAllItems/");

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.handler().handlerType(InventoryController.class))
                .andExpect(MockMvcResultMatchers.handler().methodName("getAllItems"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
    }


    @Test
    public void updateItemPriceTest() throws Exception {
        File resource = new ClassPathResource("inventory/updatePriceInInventory.json").getFile();
        String inventoryUpdateFile = new String(Files.readAllBytes(resource.toPath()));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("/inventory/updateItemPrice/{itemId}",  100)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inventoryUpdateFile);

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.handler().handlerType(InventoryController.class))
                .andExpect(MockMvcResultMatchers.handler().methodName("updateItemPrice"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
    }


    @Test
    public void updateItemCountTest() throws Exception {
        File resource = new ClassPathResource("inventory/updatePriceInInventory.json").getFile();
        String inventoryUpdateFile = new String(Files.readAllBytes(resource.toPath()));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("/inventory/updateItemCount/{updateType}/{itemId}",  "A","100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inventoryUpdateFile);

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.handler().handlerType(InventoryController.class))
                .andExpect(MockMvcResultMatchers.handler().methodName("updateItemCount"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
    }


}
