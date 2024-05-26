package com.solana.solanaApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SolanaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolanaApplication.class, args);
	}

	@RequestMapping("/api/v1/sayHello")
	public String helloWorld(){
		return "Hello world";
	}
}