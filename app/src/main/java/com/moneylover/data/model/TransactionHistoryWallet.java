package com.moneylover.data.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionHistoryWallet {
    private String date;
    private String amount;
    private boolean income;

    public static final Comparator<TransactionHistoryWallet> compareByDateDescending =
            (w1, w2) -> {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                try {
                    String cleanDate1 = w1.date.replace(", Hôm nay", "").trim();
                    String cleanDate2 = w2.date.replace(", Hôm nay", "").trim();
                    Date d1 = sdf.parse(cleanDate1);
                    Date d2 = sdf.parse(cleanDate2);
                    return d2.compareTo(d1); // mới nhất lên trước
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            };

    public static void sortByDateDescending(List<TransactionHistoryWallet> list) {
        Collections.sort(list, compareByDateDescending);
    }
}
