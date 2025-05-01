package com.moneylover.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {

    public static String formatNumberWithComma(int number) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat.format(number);
    }

}
