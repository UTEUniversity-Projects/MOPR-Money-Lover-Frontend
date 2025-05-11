package com.moneylover.data.model.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestUpdatePasswordRequest {
    private String oldPassword;

    private String newPassword;
}
