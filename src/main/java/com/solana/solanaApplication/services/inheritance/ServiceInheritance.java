package com.solana.solanaApplication.services.inheritance;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

public class ServiceInheritance {
    /**
     * The URL of the Solana RPC API.
     */
    public static final String SOLANA_RPC_URL = "https://api.mainnet-beta.solana.com";

    /**
     * The HTTP client used to make requests to the Solana RPC API.
     */
    protected static OkHttpClient httpClient = new OkHttpClient();

    /**
     * The object mapper used to serialize and deserialize JSON data.
     */
    protected static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * The number of lamports in one SOL.
     */
    public static final long LAMPORTS_PER_SOL = 1_000_000_000L;

    /**
     * Creates a new instance of ServiceInheritance.
     *
     */
    public ServiceInheritance() {
        super();
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }
}