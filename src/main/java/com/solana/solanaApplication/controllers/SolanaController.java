package com.solana.solanaApplication.controllers;

import com.solana.solanaApplication.services.SolanaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SolanaController {

    private final SolanaService solanaService;

    public SolanaController(SolanaService solanaService) {
        this.solanaService = solanaService;
    }

    @GetMapping("/balance")
    public String getBalance(@RequestParam String publicKey) {
        try {
            double balance = solanaService.getBalance(publicKey);
            return "Balance: " + balance + " SOL";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error retrieving balance";
        }
    }
}
