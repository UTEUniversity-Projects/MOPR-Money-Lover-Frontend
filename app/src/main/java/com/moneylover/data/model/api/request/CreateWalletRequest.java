package com.moneylover.data.model.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateWalletRequest {
    private String name;

    private Long currencyId;

    @Builder.Default
    private Boolean isPrimary = false;

    @Builder.Default
    private Boolean turnOnNotifications = true;

    @Builder.Default
    private Boolean chargeToTotal = true;

    private Long iconId;
}
