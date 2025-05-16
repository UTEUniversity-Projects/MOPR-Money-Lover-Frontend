package com.moneylover.data.model.api.response;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class WalletResponse implements Serializable {
    private Long id;

    private String name;

    private BigDecimal balance;

    private CurrencyResponse currency;

    private Boolean isPrimary = false;

    private Boolean turnOnNotifications = true;

    private Boolean chargeToTotal = true;

    private FileResponse icon;

    private String createdDate;

}
