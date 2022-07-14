package com.ttx.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EcommerceApplication {
	@GetMapping("/")
	public String index() {
		return "Hello eCommerce!";
	}

	public static void main(String[] args) {

		SpringApplication.run(EcommerceApplication.class, args);

	}

}
