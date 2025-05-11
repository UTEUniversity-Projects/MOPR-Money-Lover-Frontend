package com.moneylover.data.model.api.request;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCategoryOrderingRequest {
    private List<UpdateCategoryOrderingItemRequest> categories;
}
