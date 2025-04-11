package com.moneylover.utils;

import android.util.Base64;

import org.json.JSONObject;

public class JwtUtils {

    public static boolean isTokenExpired(String token) {
        try {
            // Lấy phần payload
            String[] split = token.split("\\.");
            if (split.length < 2) return true;

            String payload = split[1];

            // Base64 decode payload
            byte[] decodedBytes = Base64.decode(payload, Base64.URL_SAFE | Base64.NO_WRAP);
            String decodedPayload = new String(decodedBytes);

            JSONObject json = new JSONObject(decodedPayload);

            // Lấy thời gian hết hạn (exp), đơn vị: giây
            long exp = json.getLong("exp");
            long currentTime = System.currentTimeMillis() / 1000; // giây

            return exp < currentTime;
        } catch (Exception e) {
            e.printStackTrace();
            return true; // Nếu lỗi thì coi như token không hợp lệ
        }
    }

}
