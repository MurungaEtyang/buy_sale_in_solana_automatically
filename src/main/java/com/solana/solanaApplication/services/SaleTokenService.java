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
public class SaleTokenService extends ServiceInheritance {
    /**
     * Sends a JSON-RPC request to sell a specified amount of XYS tokens.
     *
     * @param privateKey      The private key of the seller's Solana account.
     * @param xysTokenAddress The address of the XYS token to sell.
     * @param xysAmount       The amount of XYS tokens to sell.
     * @return The JSON response from the Solana RPC call.
     * @throws IOException If there is an error in the HTTP request.
     */

    public String sellToken(String privateKey, String xysTokenAddress, long xysAmount) throws IOException {
        // Construct JSON-RPC request body for selling tokens
        String requestBody = "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"sellXYS\",\"params\":[\"" + privateKey + "\", \"" + xysTokenAddress + "\", " + xysAmount + "]}";

        RequestBody body = RequestBody.create(
                MediaType.get("application/json"),
                requestBody
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
            return jsonNode.toString();
        }
    }
}
