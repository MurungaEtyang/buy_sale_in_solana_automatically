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
public class CheckBalance extends ServiceInheritance {

    /**
     * Retrieves the balance of a Solana account.
     *
     * @param publicKey The public key of the Solana account.
     * @return The balance of the Solana account in SOL.
     * @throws IOException If there is an error communicating with the Solana RPC API.
     */
    public static double getBalance(String publicKey) throws IOException {
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
