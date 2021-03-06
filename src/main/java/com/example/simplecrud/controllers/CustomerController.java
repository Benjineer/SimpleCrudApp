/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.simplecrud.controllers;

import com.example.simplecrud.dtos.CustomerDTO;
import com.example.simplecrud.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author Oke
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @GetMapping()
    public List<CustomerDTO> list() {
        return customerService.get();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.of(customerService.get(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable Long id, @RequestBody CustomerDTO input) {
        return ResponseEntity.of(customerService.update(id, input));
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody CustomerDTO input) throws Exception {
        return ResponseEntity.of(customerService.create(input));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.of(customerService.delete(id));
    }
    
}
