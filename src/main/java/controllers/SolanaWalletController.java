package com.solana.solanaApplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.solana.solanaApplication.services.SolanaWalletService;

@RestController
@RequestMapping("/api/v1/solana")
public class SolanaWalletController {

    @Autowired
    private SolanaWalletService solanaService;

    @PostMapping("/request")
    public String requestSol(
            @RequestParam String fromAddress,
            @RequestParam String toAddress,
            @RequestParam long lamports) {
        return solanaService.requestSol(fromAddress, toAddress, lamports);
    }
}
