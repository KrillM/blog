package com.blog.controller;

import com.blog.dto.WriterFormDto;
import com.blog.entity.Writer;
import com.blog.service.WriterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class WriterControllerTest {
    @Autowired
    private WriterService ws;
    @Autowired
    private MockMvc mm;
    @Autowired
    PasswordEncoder pe;

    public Writer createWriter(String email, String password){
        WriterFormDto wfd = new WriterFormDto();
        wfd.setEmail(email);
        wfd.setName("우영우");
        wfd.setPassword(password);
        Writer writer = Writer.createWriter(wfd, pe);
        return ws.saveWriter(writer);
    }

    @Test
    @DisplayName("Login Success Test")
    public void loginSuccessTest() throws Exception{
        String email = "test@email.com";
        String password = "1234";
        this.createWriter(email,password);
        mm.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/writer/login")
                        .user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());

    }

    @Test
    @DisplayName("Login Failure Test")
    public void loginFailTest() throws Exception{
        String email = "test@email.com";
        String password = "1234";
        this.createWriter(email,password);
        mm.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/writer/login")
                        .user(email).password("12345"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }
}