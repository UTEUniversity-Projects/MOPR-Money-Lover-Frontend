package com.moneylover.data.model.api.response;

import lombok.Data;

@Data
public class AccountResponse {
    private String username;

    private String email;

    private String phone;

    private FileResponse avatar;
}
