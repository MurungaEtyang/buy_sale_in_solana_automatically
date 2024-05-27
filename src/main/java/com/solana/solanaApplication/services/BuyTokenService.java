package com.solana.solanaApplication.services;
import com.fasterxml.jackson.databind.*;
import okhttp3.*;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.*;
import java.security.spec.*;

@Service
public class BuyTokenService {

    private static final String SOLANA_RPC_URL = "https://api.mainnet-beta.solana.com";
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public BuyTokenService() {
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * This method is used to buy a Solana token using the private key of the buyer, the address of the token, and the amount of SOL to be spent.
     *
     * @param privateKey      The private key of the buyer.
     * @param xysTokenAddress The address of the token.
     * @param solAmount       The amount of SOL to be spent for the token.
     * @return The JSON response from the Solana RPC call.
     * @throws IOException If there is an error in the HTTP request.
     */
    public String buyToken(String privateKey, String xysTokenAddress, long solAmount) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        // Construct and sign transaction
        String signedTransaction = createAndSignTransaction(privateKey, xysTokenAddress, solAmount);

        // Send the signed transaction
        RequestBody body = RequestBody.create(
                MediaType.get("application/json"),
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"sendTransaction\",\"params\":[\"" + signedTransaction + "\"]}"
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


    /**
     * Creates and signs a transaction using the provided private key, token address, and amount.
     *
     * @param privateKey The private key of the transaction signer.
     * @param xysTokenAddress The address of the token.
     * @param solAmount The amount of SOL to be spent for the token.
     * @return The signed transaction.
     * @throws NoSuchAlgorithmException If the specified algorithm is not available.
     * @throws InvalidKeySpecException If the provided key specification is invalid.
     * @throws SignatureException If the signing operation fails.
     * @throws InvalidKeyException If the provided key is invalid.
     */
    private String createAndSignTransaction(String privateKey, String xysTokenAddress, float solAmount) throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        // Create the transaction data
        String transactionData = "Transaction data here";
        // Decode the private key
        byte[] privateKeyBytes = Base64.decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("Ed25519");
        PrivateKey privKey = keyFactory.generatePrivate(keySpec);

        // Sign the transaction
        Signature signature = Signature.getInstance("Ed25519");
        signature.initSign(privKey);
        signature.update(transactionData.getBytes());
        byte[] signedData = signature.sign();

        // Combine transaction data and signature
        String signedTransaction = transactionData + Base64.toBase64String(signedData);
        return signedTransaction;
    }
}
