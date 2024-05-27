package com.solana.solanaApplication.controllers;

import com.solana.solanaApplication.controllers.inheritance.BalanceFeeInheritance;
import com.solana.solanaApplication.services.SolanaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/token")
public class SolanaBalance extends BalanceFeeInheritance {
    public SolanaBalance(SolanaService solanaService) {
        super(solanaService);
    }

    /**
     * This method is a GET endpoint that retrieves the balance of a Solana account.
     *
     * @param recipientAddress The public key of the Solana account.
     * @return A JSON response indicating the balance of the account. If the balance is less than or equal to 0.02 SOL, it returns an error response. Otherwise, it returns the balance. If there is an error retrieving the balance, it returns an error response.
     *  * @param balance the balance to check
     *  * @return a ResponseEntity with the appropriate status and message
     */
    @Operation(summary = "Check Balance", description = "Check the balance of a Solana account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance retrieved successfully", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Transfer failed", content = @Content(schema = @Schema(implementation = Map.class)))
    })

    @GetMapping("/checkBalance")
    public ResponseEntity<Map<String, Object>> getBalance(
            @Parameter(description = "The address of the recipient account") @RequestParam("recipientAddress") String recipientAddress
    ) {
        Map<String, Object> response = new HashMap<>();
        try {
            double balance = solanaService.getBalance(recipientAddress);
            response.put("status", "success");
            response.put("message", "Requested successfully");
            response.put("balance", balance);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error retrieving balance: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
