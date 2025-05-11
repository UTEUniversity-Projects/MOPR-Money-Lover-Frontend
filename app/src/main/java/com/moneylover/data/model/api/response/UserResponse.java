package com.moneylover.data.model.api.response;

import java.time.Instant;

import lombok.Data;

@Data
public class UserResponse {
    private AccountResponse account;

    private String fullName;

    private Integer gender;

    private Instant birthday;
}