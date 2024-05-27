package com.solana.solanaApplication.controllers.inheritance;

import com.solana.solanaApplication.services.TransactionFeeSolanaService;

public class BalanceFeeInheritance {
    protected final TransactionFeeSolanaService transactionFeeSolanaService;

    public BalanceFeeInheritance(TransactionFeeSolanaService transactionFeeSolanaService) {
        this.transactionFeeSolanaService = transactionFeeSolanaService;
    }
}
