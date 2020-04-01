/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.simplecrud.service.impls;

import com.example.simplecrud.entities.CustomerEntity;
import com.example.simplecrud.repositories.CustomerRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 *
 * @author Oke
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        
        Optional<CustomerEntity> optCustEnt = customerRepository.findByEmail(username);
        
        CustomerEntity customerEntity = optCustEnt.orElseThrow(() -> new UsernameNotFoundException(username + "Not found"));
        return User.builder()
                .username(username)
                .password(customerEntity.getPassword())
                .authorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .roles("USER")
                .build();
    }
    
}
