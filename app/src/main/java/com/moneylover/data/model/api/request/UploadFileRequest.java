package com.moneylover.data.model.api.request;

import lombok.Builder;
import lombok.Data;
import okhttp3.MultipartBody;

@Data
@Builder
public class UploadFileRequest {
    private MultipartBody.Part file;

    private String fileType;
}
