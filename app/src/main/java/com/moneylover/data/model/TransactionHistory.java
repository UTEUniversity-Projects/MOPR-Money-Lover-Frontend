package com.moneylover.data.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionHistory {
    private String category;
    private String transactionCount;
    private List<TransactionHistoryWallet> wallets;
}
