package com.solana.solanaApplication.controllers;

import com.solana.solanaApplication.services.SolanaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SolanaController {

    private final SolanaService solanaService;

    public SolanaController(SolanaService solanaService) {
        this.solanaService = solanaService;
    }

    /**
     * This method is a GET endpoint that retrieves the balance of a Solana account.
     *
     * @param publicKey The public key of the Solana account.
     * @return A string indicating the balance of the account. If the balance is less than or equal to 0.02 SOL, it returns "insufficient balance {balance} SOL". Otherwise, it returns "Balance: {balance} SOL". If there is an error retrieving the balance, it returns "Error retrieving balance".
     * @throws IOException If there is an error retrieving the balance.
     */
    @GetMapping("/balance")
    public String getBalance(@RequestParam String publicKey) {
        try {
            double balance = solanaService.getBalance(publicKey);
            if(balance <= 0.02) {
                return "insufficient balance " + balance + " SOL";
            }else{
                return "Balance: " + balance + " SOL";
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "Error retrieving balance";
        }
    }

    /**
     * This method is a POST endpoint that transfers SOL from one account to another.
     *
     * @param senderPrivateKey The private key of the sender account.
     * @param recipientAddress The address of the recipient account.
     * @param lamports The amount of lamports to transfer.
     * @return A string indicating the result of the transfer.
     */

    @PostMapping("/transfer")
    public String transferSOL(
            @RequestParam("senderPrivateKey") String senderPrivateKey,
            @RequestParam("recipientAddress") String recipientAddress,
            @RequestParam("lamports") String lamports
    ) {
        String response = (String) solanaService.sendTransactionFee(senderPrivateKey, recipientAddress, lamports);
        return "Transfer successful: " + response;
    }


}


