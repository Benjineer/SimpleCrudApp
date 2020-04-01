/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.simplecrud.dtos;

/**
 *
 * @author Oke
 */
public class AuthResponse {
    
    private final String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }
    
    public String getJwt() {
        return jwt;
    }
    
}
