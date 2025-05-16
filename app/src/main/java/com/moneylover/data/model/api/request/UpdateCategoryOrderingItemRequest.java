package com.moneylover.data.model.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCategoryOrderingItemRequest {
    private Long categoryId;

    private Integer ordering;
}
