package com.moneylover.data.model.api.request;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequest {
    private String username;

    private String phone;

    private Long avatarId;

    private String fullName;

    private Integer gender;

    private Instant birthday;
}
