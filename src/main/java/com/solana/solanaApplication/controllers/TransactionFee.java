package com.solana.solanaApplication.controllers;

import com.solana.solanaApplication.services.TransactionFeeSolanaService;
import com.solana.solanaApplication.controllers.inheritance.BalanceFeeInheritance;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/token")
public class TransactionFee extends BalanceFeeInheritance {

    public TransactionFee(TransactionFeeSolanaService transactionFeeSolanaService) {
        super(transactionFeeSolanaService);
    }

    /**
     * Transfers SOL from one account to another.
     *
     * @param senderPrivateKey The private key of the sender account.
     * @param recipientAddress The address of the recipient account.
     * @param lamports The amount of lamports to transfer.
     * @return A JSON response indicating the result of the transfer.
     */
    @Operation(summary = "Transaction fee", description = "Transfers SOL from one account to another.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transfer successful", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Transfer failed", content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @GetMapping("/transfer")
    public ResponseEntity<Map<String, Object>> transferSOL(
            @Parameter(description = "The private key of the sender account") @RequestParam("senderPrivateKey") String senderPrivateKey,
            @Parameter(description = "The address of the recipient account") @RequestParam("recipientAddress") String recipientAddress,
            @Parameter(description = "The amount of solana to transfer") @RequestParam("SOL eg 1 SOL") float lamports
    ) {
        Map<String, Object> response = new HashMap<>();
        try {
            String transactionResult = (String) transactionFeeSolanaService.sendTransactionFee(senderPrivateKey, recipientAddress, Float.parseFloat(String.valueOf(lamports)));
            response.put("status", "success");
            response.put("message", "Transfer successful");
            response.put("transactionResult", transactionResult);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Transfer failed: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
