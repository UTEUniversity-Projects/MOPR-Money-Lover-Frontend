package com.moneylover.data.model.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestRegisterRequest {
    private String email;
    private String password;
    private String recaptchaResponse;
}
