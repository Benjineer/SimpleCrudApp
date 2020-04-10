/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.simplecrud.controllers;

import com.example.simplecrud.dtos.CustomerDTO;
import com.example.simplecrud.services.CustomerService;
import com.example.simplecrud.services.impls.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author Oke
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private CustomerDTO customerDTO;
    
    public CustomerControllerTest() {
    }
    
    @TestConfiguration
    static class CustomerControllerTestContextConfig{

        @Bean
        public UserDetailsService userDetailsService(){
            return Mockito.mock(UserDetailsService.class);
        }

        @Bean
        public JwtService jwtService(){
            return Mockito.mock(JwtService.class);
        }
    }
    
    @Before
    public void setUp() {

        customerDTO = new CustomerDTO();
        customerDTO.setAddress("address");
        customerDTO.setEmail("email@mail.com");
        customerDTO.setName("name");
        customerDTO.setPassword("password");
        customerDTO.setPhoneNo("070192225555");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of list method, of class CustomerController.
     * @throws java.lang.Exception
     */
    @Test
    public void testList() throws Exception {
        
        when(customerService.get()).thenReturn(new ArrayList<>());
        
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    /**
     * Test of get method, of class CustomerController.
     */
    @Test
    public void testGet() throws Exception {

        when(customerService.get(anyLong())).thenReturn(Optional.of(customerDTO));

        String expResponse = objectMapper.writeValueAsString(customerDTO);

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expResponse))
                .andExpect(jsonPath("password").doesNotHaveJsonPath());
    }

    /**
     * Test of put method, of class CustomerController.
     */
    @Test
    public void testPut() throws Exception {
        when(customerService.update(anyLong(),any(CustomerDTO.class))).thenReturn(Optional.of(customerDTO));

        String body = objectMapper.writeValueAsString(customerDTO);

        mockMvc.perform(put("/customers/1").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(body))
                .andExpect(jsonPath("password").doesNotExist());
    }

    /**
     * Test of put method, of class CustomerController.
     * When Id is not Found
     */
    @Test
    public void testPutWhenIdNotFound() throws Exception {
        when(customerService.update(anyLong(),any(CustomerDTO.class))).thenReturn(Optional.empty());

        String body = objectMapper.writeValueAsString(customerDTO);

        mockMvc.perform(put("/customers/1").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Test of post method, of class CustomerController.
     */
    @Test
    public void testPost() throws Exception {
        when(customerService.create(any(CustomerDTO.class))).thenReturn(Optional.of(customerDTO));

        String body = objectMapper.writeValueAsString(customerDTO);

        mockMvc.perform(post("/customers").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(body))
                .andExpect(jsonPath("password").doesNotExist());
    }

    /**
     * Test of post method, of class CustomerController.
     * When email already exists
     */
    @Test
    public void testPostWhenEmailAlreadyExists() throws Exception {
        when(customerService.create(any(CustomerDTO.class))).thenThrow(new Exception("Customer already exists"));

        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("Customer already exists");

        String body = objectMapper.writeValueAsString(customerDTO);

        mockMvc.perform(post("/customers").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test of delete method, of class CustomerController.
     */
    @Test
    public void testDelete() throws Exception {
        when(customerService.delete(anyLong())).thenReturn(Optional.of(1L));

        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("1"));
    }

    /**
     * Test of delete method, of class CustomerController.
     * When Id not found
     */
    @Test
    public void testDeleteWhenIdNotFound() throws Exception {
        when(customerService.delete(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isNotFound());
    }
    
}
