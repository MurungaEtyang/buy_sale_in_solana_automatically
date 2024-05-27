package com.solana.solanaApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;


@Lazy(value = false)
@SpringBootApplication
public class SolanaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolanaApplication.class, args);
	}

}