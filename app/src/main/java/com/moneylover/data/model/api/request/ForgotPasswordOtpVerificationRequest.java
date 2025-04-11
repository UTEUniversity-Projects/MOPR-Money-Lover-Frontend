package com.moneylover.data.model.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ForgotPasswordOtpVerificationRequest {
    private String otp;
    private String newPassword;
    private String token;
}
