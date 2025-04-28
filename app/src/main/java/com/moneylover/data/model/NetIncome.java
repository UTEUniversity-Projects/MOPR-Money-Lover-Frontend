package com.moneylover.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NetIncome {
    private String date;
    private int expenditure;
    private int income;
}
