package com.example.simplecrud;

import com.example.simplecrud.controllers.AuthController;
import com.example.simplecrud.controllers.CustomerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SimplecrudApplicationTests {
    
    @Autowired
    private CustomerController customerController;
    
    @Autowired
    private AuthController authController;

	@Test
	void contextLoads() {
            assertThat(customerController).isNotNull();
            assertThat(authController).isNotNull();
	}

}
