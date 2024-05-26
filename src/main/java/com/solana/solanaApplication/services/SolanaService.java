package com.solana.solanaApplication.services;

import com.solana.solanaApplication.inheritance.ServiceInheritance;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import java.io.IOException;
import okhttp3.*;

@Service
public class SolanaService extends ServiceInheritance  {



    /**
     * Retrieves the balance of a Solana account.
     *
     * @param publicKey The public key of the Solana account.
     * @return The balance of the Solana account in SOL.
     * @throws IOException If there is an error communicating with the Solana RPC API.
     */
    public double getBalance(String publicKey) throws IOException {
        RequestBody body = RequestBody.create(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"getBalance\",\"params\":[\"" + publicKey + "\"]}",
                MediaType.get("application/json")
        );

        Request request = new Request.Builder()
                .url(SOLANA_RPC_URL)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            long balanceLamports = jsonNode.path("result").path("value").asLong();
            return (double) balanceLamports / LAMPORTS_PER_SOL;
        }
    }

    /**
     * Sends a transaction with a fee to transfer Solana from one address to another.
     *
     * @param senderPrivateKey The private key of the sender address.
     * @param recipientAddress The public key of the recipient address.
     * @param amountSolana The amount of Solana to transfer in Solana units (1 SOL = 1_000_000_000 lamports).
     * @return The transaction hash of the transfer.
     * @throws RuntimeException If there is an error during the transaction.
     */
    public Object sendTransactionFee(String senderPrivateKey, String recipientAddress, String amountSolana) {

        // 1 SOL = 1_000_000_000 lamports
        long amountLamports = Long.parseLong(amountSolana) * LAMPORTS_PER_SOL;
        String requestBody = "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"transferSOL\",\"params\":[\"" + senderPrivateKey + "\", \"" + recipientAddress + "\", " + amountLamports + "]}";

        RequestBody body = RequestBody.create(requestBody, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(SOLANA_RPC_URL)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("result").get("value").asText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
