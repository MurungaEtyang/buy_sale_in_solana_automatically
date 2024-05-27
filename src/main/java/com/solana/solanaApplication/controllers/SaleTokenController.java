package com.solana.solanaApplication.controllers;

import com.solana.solanaApplication.services.SaleTokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vi/token")

public class SaleTokenController {

    private final SaleTokenService saleTokenService;

    public SaleTokenController(SaleTokenService saleTokenService) {
        this.saleTokenService = saleTokenService;
    }


    @PostMapping("/sale")
    public String saleToken(
            @RequestParam String privateKey,
            @RequestParam String tokenAddress,
            @RequestParam float amount
    ) {
        try {
            String response = saleTokenService.sellToken(privateKey, tokenAddress, (long) amount);
            return "Token sale successful: " + response;
        } catch (Exception e) {
            e.printStackTrace();
            return "Token sale failed";
        }
    }


}
