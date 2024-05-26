package com.solana.solanaApplication.controllers;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class HTMLController {

    @RequestMapping("/api/v1/html")
    public ResponseEntity<String> index() {
        try{
            // Load HTML content
            Resource resource = new ClassPathResource("index.html");
            byte[] bytes = Files.readAllBytes(Paths.get(resource.getURI()));
            String content = new String(bytes);
            return ResponseEntity.ok().body(content);
        }
        catch(IOException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
