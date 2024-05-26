package com.solana.solanaApplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.solana.solanaApplication.services.SolanaWalletService;

@RestController
@RequestMapping("/api/v1/solana")
public class SolanaWalletController {

    @Autowired
    private SolanaWalletService solanaWalletService;

    @PostMapping("/request")
    public String requestSol(
            @RequestParam String fromAddress,
            @RequestParam String toAddress,
            @RequestParam long lamports) {
        return solanaWalletService.requestSol(fromAddress, toAddress, lamports);
    }

    @RequestMapping("/message")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
