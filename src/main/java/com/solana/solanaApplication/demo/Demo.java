package com.solana.solanaApplication.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class Demo {

    @RequestMapping("/message")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
