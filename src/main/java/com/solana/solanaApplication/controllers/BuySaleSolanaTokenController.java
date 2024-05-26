package com.solana.solanaApplication.controllers;

import com.solana.solanaApplication.services.BuySaleSolanaTokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
class BuySaleSolanaTokenController {

    private final BuySaleSolanaTokenService solanaService;

    public BuySaleSolanaTokenController(BuySaleSolanaTokenService solanaService) {
        this.solanaService = solanaService;
    }

    @PostMapping("/buy-token")
    public String buyToken(
            @RequestParam String privateKey,
            @RequestParam String tokenAddress,
            @RequestParam long amount
    ) {
        try {
            String response = solanaService.buyToken(privateKey, tokenAddress, amount);
            return "Token purchase successful: " + response;
        } catch (IOException e) {
            e.printStackTrace();
            return "Token purchase failed";
        }
    }

    @PostMapping("/sell-token")
    public String sellToken(
            @RequestParam String privateKey,
            @RequestParam String tokenAddress,
            @RequestParam long amount
    ) {
        try {
            String response = solanaService.sellToken(privateKey, tokenAddress, amount);
            return "Token sale successful: " + response;
        } catch (IOException e) {
            e.printStackTrace();
            return "Token sale failed";
        }
    }
}
