/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.simplecrud.repositories;

import com.example.simplecrud.entities.CustomerEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Oke
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    
    Optional<CustomerEntity> findByEmail(String email);
    
}
