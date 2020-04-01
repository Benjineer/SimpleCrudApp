/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.simplecrud.service.impls;

import com.example.simplecrud.dtos.CustomerDTO;
import com.example.simplecrud.entities.CustomerEntity;
import com.example.simplecrud.repositories.CustomerRepository;
import com.example.simplecrud.services.CustomerService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 *
 * @author Oke
 */
@Service
public class CustomerServiceImpl implements CustomerService{
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<CustomerDTO> create(CustomerDTO customerDTO) {
        
        Optional<CustomerEntity> optCustEnt = customerRepository.findByEmail(customerDTO.getEmail());
        if(optCustEnt.isPresent()) return Optional.empty();
        
        CustomerEntity customerEntity = modelMapper.map(customerDTO, CustomerEntity.class);
        customerEntity.setPassword(BCrypt.hashpw(customerEntity.getPassword(), BCrypt.gensalt()));
        
        CustomerEntity savedCustEnt = customerRepository.save(customerEntity);
        
        CustomerDTO savedCustDTO = modelMapper.map(savedCustEnt, CustomerDTO.class);
        return Optional.of(savedCustDTO);
    }

    @Override
    public Optional<CustomerDTO> get(Long id) {
        return customerRepository.findById(id).map((t) -> {
             return modelMapper.map(t, CustomerDTO.class);
        });
    }

    @Override
    public List<CustomerDTO> get() {
       return customerRepository.findAll().stream().map((t) -> {
           return modelMapper.map(t, CustomerDTO.class);
       }).collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> update(Long id, CustomerDTO customerDTO) {
        Optional<CustomerEntity> optCustEnt = customerRepository.findById(id);
        if(!optCustEnt.isPresent()) return Optional.empty();
        
        CustomerEntity customerEntity = optCustEnt.get();
        customerEntity.setEmail(customerDTO.getEmail());
        customerEntity.setAddress(customerDTO.getAddress());
        customerEntity.setName(customerDTO.getName());
        customerEntity.setPhoneNo(customerDTO.getPhoneNo());
        
        CustomerEntity savedCustEnt = customerRepository.save(customerEntity);
        
        return Optional.of(modelMapper.map(savedCustEnt, CustomerDTO.class));
    }

    @Override
    public Optional<Long> delete(Long id) {
        Optional<CustomerEntity> optCustEnt = customerRepository.findById(id);
        if(optCustEnt.isPresent()) return Optional.empty();
        customerRepository.deleteById(id);
        return Optional.of(id);
    }
    
}
