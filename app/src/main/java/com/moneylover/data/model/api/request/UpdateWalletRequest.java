package com.moneylover.data.model.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateWalletRequest {
    private Long id;

    private String name;

    private Long currencyId;

    private Boolean isPrimary;

    private Boolean turnOnNotifications;

    private Boolean chargeToTotal;

    private Long iconId;
}
