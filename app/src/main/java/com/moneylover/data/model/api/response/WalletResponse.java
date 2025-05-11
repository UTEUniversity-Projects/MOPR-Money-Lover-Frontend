package com.moneylover.data.model.api.response;

import java.time.Instant;

import lombok.Data;

@Data
public class WalletResponse {
    private Long id;

    private String name;

    private CurrencyResponse currency;

    private Double balance;

    private Boolean isPrimary = false;

    private Boolean turnOnNotifications = true;

    private Boolean chargeToTotal = true;

    private FileResponse icon;

    private String createdDate;

}
