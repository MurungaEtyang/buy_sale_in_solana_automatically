package com.solana.solanaApplication.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solana.solanaApplication.inheritance.ServiceInheritance;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BuySaleSolanaTokenService extends ServiceInheritance {

    public String buyToken(String privateKey, String xysTokenAddress, long solAmount) throws IOException {
        // Construct JSON-RPC request body for buying tokens
        String requestBody = "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"buyXYS\",\"params\":[\"" + privateKey + "\", \"" + xysTokenAddress + "\", " + solAmount + "]}";

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
