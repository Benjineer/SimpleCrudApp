/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.simplecrud.controllers;

import com.example.simplecrud.services.impls.JwtService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Oke
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AuthenticationManager authenticationManager;
    
    @MockBean
    private UserDetailsService userDetailsService;
    
    @MockBean
    private JwtService jwtService;
    
    private UsernamePasswordAuthenticationToken upat;
    
    private User user;
    
    public AuthControllerTest() {
    }
    
    @Before
    public void setUp() {
        
        upat = new UsernamePasswordAuthenticationToken("ben.fen@mail.com", "password");
        
        user = new User("ben.fen@mail.com", "password", new ArrayList<>());
    }

    /**
     * Test of authenticate method, of class AuthController.
     * @throws java.lang.Exception
     */
    @Test
    public void testAuthenticate() throws Exception {
        
        when(authenticationManager.authenticate(any(Authentication.class))).
                thenReturn(upat);
        
        when(userDetailsService.loadUserByUsername(anyString()))
                .thenReturn(user);

        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZW4uZmVuQG1haWwuY29tIiwiZXhwIjoxNTg1NzkxMTQyLCJpYXQiOjE1ODU3NTUxNDJ9.2WDzB8GhPgElD8lEnib7rueEopoP9sTFglLBObz1THY";

        when(jwtService.generateToken(any(UserDetails.class)))
                .thenReturn(jwt);
        
        /**
         * {
            "username":"ben.fen@mail.com",
            "password":"fen"
           }
         */
        String content = "{\"username\":\"ben.fen@mail.com\",\"password\":\"fen\"}";
        mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("jwt").value(jwt));

    }
    
}
