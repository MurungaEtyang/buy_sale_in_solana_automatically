package com.solana.solanaApplication.controllers;

import com.solana.solanaApplication.services.BuyTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/token")
public class BuyTokenController {

    private final BuyTokenService solanaService;

    public BuyTokenController(BuyTokenService solanaService) {
        this.solanaService = solanaService;
    }

    @Operation(summary = "Buy Token", description = "Buy solana token using private key")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transfer successful", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Transfer failed", content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @PostMapping("/buy")
    public ResponseEntity<Map<String, Object>> buyToken(
            @Parameter(description = "The private key of the sender account") @RequestParam("senderPrivateKey") String senderPrivateKey,
            @Parameter(description = "The address of the recipient account") @RequestParam("recipientAddress") String recipientAddress,
            @Parameter(description = "The amount of solana to be bought") @RequestParam("lamports") float amount
    ) {
        Map<String, Object> response = new HashMap<>();
        try {
            String result = solanaService.buyToken(senderPrivateKey, recipientAddress, (long) amount);
            response.put("status", "success");
            response.put("message", "Token purchase successful");
            response.put("data", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Token purchase failed: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | SignatureException | InvalidKeyException e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Token purchase failed: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
