package com.moneylover.data.model.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateEmailRequest {
    private String otp;

    private String token;
}
