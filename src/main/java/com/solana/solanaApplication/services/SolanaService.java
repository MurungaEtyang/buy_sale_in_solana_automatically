package com.solana.solanaApplication.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SolanaService {

    private static final String SOLANA_RPC_URL = "https://api.mainnet-beta.solana.com";
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private static final long LAMPORTS_PER_SOL = 1_000_000_000L;

    public SolanaService() {
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

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
}
