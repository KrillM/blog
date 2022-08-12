package com.blog.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class ReadControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Content Registration authority Test")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void createFormAdminTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/read/create"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Content Registration normal member authority Test")
    @WithMockUser(username = "user", roles = "USER")
    public void createFormNotAdminTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/read/create"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}