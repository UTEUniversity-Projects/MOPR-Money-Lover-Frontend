package com.moneylover.data.model.api.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class FileResponse implements Serializable {
    private Long id;

    private String fileName;

    private String fileUrl;

    private String fileType;
}
