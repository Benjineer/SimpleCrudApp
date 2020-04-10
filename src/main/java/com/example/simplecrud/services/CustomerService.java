/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.simplecrud.services;

import com.example.simplecrud.dtos.CustomerDTO;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Oke
 */
public interface CustomerService {
    
    Optional<CustomerDTO> create(CustomerDTO customerDTO) throws Exception;
    
    Optional<CustomerDTO> get(Long id);
    
    List<CustomerDTO> get();
    
    Optional<CustomerDTO> update(Long id, CustomerDTO customerDTO);
    
    Optional<Long> delete(Long id);
    
}
