package com.solana.solanaApplication.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.solana.solanaApplication.services.inheritance.ServiceInheritance;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TransactionFeeSolanaService extends ServiceInheritance  {


    /**
     * Sends a transaction with a fee to transfer Solana from one address to another.
     *
     * @param senderPrivateKey The private key of the sender address.
     * @param recipientAddress The public key of the recipient address.
     * @param amountSolana The amount of Solana to transfer in Solana units (1 SOL = 1_000_000_000 lamports).
     * @return The transaction hash of the transfer.
     * @throws RuntimeException If there is an error during the transaction.
     */
    public Object sendTransactionFee(String senderPrivateKey, String recipientAddress, float amountSolana) {

        // 1 SOL = 1_000_000_000 lamports
        long amountLamports = (long) (amountSolana * LAMPORTS_PER_SOL);
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
