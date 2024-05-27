package com.solana.solanaApplication.controllers.inheritance;

import com.solana.solanaApplication.services.SolanaService;

public class BalanceFeeInheritance {
    protected final SolanaService solanaService;

    public BalanceFeeInheritance(SolanaService solanaService) {
        this.solanaService = solanaService;
    }
}
