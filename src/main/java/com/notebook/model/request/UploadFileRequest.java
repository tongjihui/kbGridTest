package com.notebook.model.request;

import lombok.Data;

@Data
public class UploadFileRequest {
    private String purpose;
    private String title;
    private String content;
    private String url;
} 