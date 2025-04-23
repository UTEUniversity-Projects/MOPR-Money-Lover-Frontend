package com.moneylover.data.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Wallet implements Serializable {
    private int icon;
    private String name;
    private int balance;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Wallet wallet = (Wallet) obj;
        return icon == wallet.icon && name.equals(wallet.name) && balance == wallet.balance;
    }

}
