package com.solana.solanaApplication.controllers;

import com.solana.solanaApplication.services.SaleTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/vi/token")

public class SaleTokenController {

    private final SaleTokenService saleTokenService;

    public SaleTokenController(SaleTokenService saleTokenService) {
        this.saleTokenService = saleTokenService;
    }

    @Operation(summary = "sale Token", description = "Ssale solana token using private key")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transfer successful", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Transfer failed", content = @Content(schema = @Schema(implementation = Map.class)))
    })

    @GetMapping("/sale")
    public String saleToken(
            @Parameter(description = "The private key of the sender account") @RequestParam("senderPrivateKey") String senderPrivateKey,
            @Parameter(description = "The address of the recipient account") @RequestParam("recipientAddress") String recipientAddress,
            @Parameter(description = "The amount of solana to be bought") @RequestParam("SOL eg 1 SOL") float amount
    ) {
        try {
            String response = saleTokenService.sellToken(senderPrivateKey, recipientAddress, (long) amount);
            return "Token sale successful: " + response;
        } catch (Exception e) {
            e.printStackTrace();
            return "Token sale failed";
        }
    }


}
