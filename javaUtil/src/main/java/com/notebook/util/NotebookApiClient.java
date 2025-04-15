package com.notebook.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.notebook.config.ApiConfig;
import com.notebook.model.request.*;
import com.notebook.model.response.*;
import cn.hutool.core.lang.TypeReference;

import java.io.IOException;
import java.util.List;

public class NotebookApiClient {
    private final ApiConfig apiConfig;

    public NotebookApiClient(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    // 获取文件列表
    public FileListResponse getFileList() throws IOException {
        String url = String.format("%s/orgs/%s/users/%s/files",
                apiConfig.getBaseUrl(), apiConfig.getOrgId(), apiConfig.getUserId());

        HttpResponse response = HttpRequest.get(url)
                .header("Content-Type", "application/json")
                .execute();

        ApiResponse<FileListResponse> apiResponse = JSONUtil.toBean(response.body(),
                new TypeReference<ApiResponse<FileListResponse>>() {}, false);

        return apiResponse.getData();
    }

    // 文件上传
    public FileUploadResponse uploadFile(UploadFileRequest request) throws IOException {
        String url = String.format("%s/orgs/%s/users/%s/files",
                apiConfig.getBaseUrl(), apiConfig.getOrgId(), apiConfig.getUserId());

        HttpResponse response = HttpRequest.post(url)
                .header("Content-Type", "application/json")
                .body(JSONUtil.toJsonStr(request))
                .execute();

        ApiResponse<FileUploadResponse> apiResponse = JSONUtil.toBean(response.body(),
                new TypeReference<ApiResponse<FileUploadResponse>>() {}, false);

        return apiResponse.getData();
    }

    // 获取文件解析进度
    public String getFileParseProgress(String fileId) throws IOException {
        String url = String.format("%s/orgs/%s/users/%s/files/%s/progress",
                apiConfig.getBaseUrl(), apiConfig.getOrgId(), apiConfig.getUserId(), fileId);

        HttpResponse response = HttpRequest.get(url)
                .header("Content-Type", "application/json")
                .execute();

        return response.body();
    }

    // 删除文件
    public void deleteFile(String fileId) throws IOException {
        String url = String.format("%s/orgs/%s/users/%s/files/%s",
                apiConfig.getBaseUrl(), apiConfig.getOrgId(), apiConfig.getUserId(), fileId);

        HttpRequest.delete(url)
                .header("Content-Type", "application/json")
                .execute();
    }

    // 创建对话
    public ConversationResponse createConversation(CreateConversationRequest request) throws IOException {
        String url = String.format("%s/orgs/%s/users/%s/conversations",
                apiConfig.getBaseUrl(), apiConfig.getOrgId(), apiConfig.getUserId());

        HttpResponse response = HttpRequest.post(url)
                .header("Content-Type", "application/json")
                .body(JSONUtil.toJsonStr(request))
                .execute();

        ApiResponse<ConversationResponse> apiResponse = JSONUtil.toBean(response.body(),
                new TypeReference<ApiResponse<ConversationResponse>>() {}, false);

        return apiResponse.getData();
    }

    // 更新对话
    public void updateConversation(String conversationId, List<ConvUpdateEvent> events) throws IOException {
        String url = String.format("%s/orgs/%s/users/%s/conversations/%s",
                apiConfig.getBaseUrl(), apiConfig.getOrgId(), apiConfig.getUserId(), conversationId);

        HttpRequest.put(url)
                .header("Content-Type", "application/json")
                .body(JSONUtil.toJsonStr(events))
                .execute();
    }

    // 发送问题并获取回答
    public String sendQuestion(String conversationId, ConversationEventRequest request) throws IOException {
        String url = String.format("%s/orgs/%s/users/%s/conversations/%s/events",
                apiConfig.getBaseUrl(), apiConfig.getOrgId(), apiConfig.getUserId(), conversationId);

        HttpResponse response = HttpRequest.post(url)
                .header("Content-Type", "application/json")
                .body(JSONUtil.toJsonStr(request))
                .execute();

        return response.body();
    }

    // 删除对话
    public void deleteConversation(String conversationId) throws IOException {
        String url = String.format("%s/orgs/%s/users/%s/conversations/%s",
                apiConfig.getBaseUrl(), apiConfig.getOrgId(), apiConfig.getUserId(), conversationId);

        HttpRequest.delete(url)
                .header("Content-Type", "application/json")
                .execute();
    }

    // 获取对话历史记录
    public ConversationHistoryResponse getConversationHistory(String conversationId) throws IOException {
        String url = String.format("%s/orgs/%s/users/%s/conversations/%s/history",
                apiConfig.getBaseUrl(), apiConfig.getOrgId(), apiConfig.getUserId(), conversationId);

        HttpResponse response = HttpRequest.get(url)
                .header("Content-Type", "application/json")
                .execute();

        ApiResponse<ConversationHistoryResponse> apiResponse = JSONUtil.toBean(response.body(),
                new TypeReference<ApiResponse<ConversationHistoryResponse>>() {}, false);

        return apiResponse.getData();
    }
} 