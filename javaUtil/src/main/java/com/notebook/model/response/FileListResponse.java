package com.notebook.model.response;

import lombok.Data;
import java.util.List;

@Data
public class FileListResponse {
    private List<FileInfo> files;

    @Data
    public static class FileInfo {
        private String fileId;
        private String fileName;
        private String fileType;
        private Long fileSize;
        private String uploadTime;
        private String status;
    }
}