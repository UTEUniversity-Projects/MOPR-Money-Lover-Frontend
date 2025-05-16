package com.moneylover.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LatestTransaction {
    private String category;
    private String date;
    private String formattedAmount;
    private int categoryIconResId;
    private int subIconResId;
}
