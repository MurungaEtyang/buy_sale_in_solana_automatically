package com.solana.solanaApplication.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SolanaWalletService {

    private OkHttpClient httpClient;
    private String rpcUrl;

    /**
     * Initializes the SolanaService by creating an OkHttpClient and setting the RPC URL.
     */

    public void SolanaService() {
        this.httpClient = new OkHttpClient();
        this.rpcUrl = "https://api.mainnet-beta.solana.com";
    }

    /**
     * This method sends a request to the Solana RPC API to transfer SOL from one address to another.
     *
     * @param fromAddress The address from which the SOL will be transferred.
     * @param toAddress   The address to which the SOL will be transferred.
     * @param lamports    The amount of lamports to be transferred.
     * @return A string indicating the success or failure of the transfer.
     */
    public String requestSol(String fromAddress, String toAddress, long lamports) {
        try {

            JsonObject params = new JsonObject();
            params.addProperty("from", fromAddress);
            params.addProperty("to", toAddress);
            params.addProperty("lamports", lamports);

            JsonArray paramsArray = new JsonArray();
            paramsArray.add(params);

            JsonObject requestPayload = new JsonObject();
            requestPayload.addProperty("jsonrpc", "2.0");
            requestPayload.addProperty("id", 1);
            requestPayload.addProperty("method", "sendTransaction");
            requestPayload.add("params", paramsArray);

            RequestBody body = RequestBody.create(
                    requestPayload.toString(),
                    MediaType.get("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(rpcUrl)
                    .post(body)
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }
}
