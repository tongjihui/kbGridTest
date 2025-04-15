package com.notebook.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.notebook.config.ApiConfig;
import com.notebook.model.request.*;
import com.notebook.model.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotebookApiClientTest {

    @Mock
    private ApiConfig apiConfig;

    private NotebookApiClient apiClient;

    @BeforeEach
    void setUp() {
        apiClient = new NotebookApiClient(apiConfig);

        // 设置API配置
        given(apiConfig.getBaseUrl()).willReturn("https://api-paas-di.yunxuetang.com.cn/ai-orchestrator/v1");
        given(apiConfig.getOrgId()).willReturn("513036ab-47e2-41ea-944d-41799585fca5");
        given(apiConfig.getUserId()).willReturn("20da2e5d-a78c-418f-b1fa-221542899395");
    }

    @Test
    void testGetFileList() throws IOException {
        // curl -X GET "https://api-paas-di.yunxuetang.com.cn/ai-orchestrator/v1/orgs/513036ab-47e2-41ea-944d-41799585fca5/users/20da2e5d-a78c-418f-b1fa-221542899395/files" \
        // -H "Content-Type: application/json"

        // 准备测试数据
        FileListResponse expectedResponse = new FileListResponse();
        FileListResponse.FileInfo fileInfo = new FileListResponse.FileInfo();
        fileInfo.setFileId("test-file-id");
        fileInfo.setFileName("test.txt");
        fileInfo.setStatus("completed");
        expectedResponse.setFiles(Arrays.asList(fileInfo));

        ApiResponse<FileListResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(0);
        apiResponse.setMessage("success");
        apiResponse.setData(expectedResponse);

        // 执行测试
        FileListResponse response = apiClient.getFileList();

        // 验证结果
        assertNotNull(response);
        assertNotNull(response.getFiles());
        assertFalse(response.getFiles().isEmpty());
        assertEquals("test-file-id", response.getFiles().get(0).getFileId());
    }

    @Test
    void testUploadFile() throws IOException {
        // curl -X POST "https://api-paas-di.yunxuetang.com.cn/ai-orchestrator/v1/orgs/513036ab-47e2-41ea-944d-41799585fca5/users/20da2e5d-a78c-418f-b1fa-221542899395/files" \
        // -H "Content-Type: application/json" \
        // -d '{
        //     "purpose": "chat",
        //     "title": "test.txt",
        //     "content": "test content"
        // }'

        // 准备测试数据
        UploadFileRequest request = new UploadFileRequest();
        request.setPurpose("chat");
        request.setTitle("test.txt");
        request.setContent("test content");

        FileUploadResponse expectedResponse = new FileUploadResponse();
        expectedResponse.setFileId("test-file-id");

        ApiResponse<FileUploadResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(0);
        apiResponse.setMessage("success");
        apiResponse.setData(expectedResponse);

        // 执行测试
        FileUploadResponse response = apiClient.uploadFile(request);

        // 验证结果
        assertNotNull(response);
        assertEquals("test-file-id", response.getFileId());
    }

    @Test
    void testGetFileParseProgress() throws IOException {
        // curl -X GET "https://api-paas-di.yunxuetang.com.cn/ai-orchestrator/v1/orgs/513036ab-47e2-41ea-944d-41799585fca5/users/20da2e5d-a78c-418f-b1fa-221542899395/files/test-file-id/progress" \
        // -H "Content-Type: application/json"

        // 准备测试数据
        String fileId = "test-file-id";
        String expectedResponse = "{\"status\":\"processing\"}";

        // 执行测试
        String response = apiClient.getFileParseProgress(fileId);

        // 验证结果
        assertNotNull(response);
    }

    @Test
    void testDeleteFile() throws IOException {
        // curl -X DELETE "https://api-paas-di.yunxuetang.com.cn/ai-orchestrator/v1/orgs/513036ab-47e2-41ea-944d-41799585fca5/users/20da2e5d-a78c-418f-b1fa-221542899395/files/test-file-id" \
        // -H "Content-Type: application/json"

        // 准备测试数据
        String fileId = "test-file-id";

        // 执行测试
        assertDoesNotThrow(() -> apiClient.deleteFile(fileId));
    }

    @Test
    void testCreateConversation() throws IOException {
        // curl -X POST "https://api-paas-di.yunxuetang.com.cn/ai-orchestrator/v1/orgs/513036ab-47e2-41ea-944d-41799585fca5/users/20da2e5d-a78c-418f-b1fa-221542899395/conversations" \
        // -H "Content-Type: application/json" \
        // -d '{
        //     "title": "Test Conversation"
        // }'

        // 准备测试数据
        CreateConversationRequest request = new CreateConversationRequest();
        request.setTitle("Test Conversation");

        ConversationResponse expectedResponse = new ConversationResponse();
        expectedResponse.setId("test-conv-id");
        expectedResponse.setTitle("Test Conversation");

        ApiResponse<ConversationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(0);
        apiResponse.setMessage("success");
        apiResponse.setData(expectedResponse);

        // 执行测试
        ConversationResponse response = apiClient.createConversation(request);

        // 验证结果
        assertNotNull(response);
        assertEquals("test-conv-id", response.getId());
        assertEquals("Test Conversation", response.getTitle());
    }

    @Test
    void testUpdateConversation() throws IOException {
        // curl -X PUT "https://api-paas-di.yunxuetang.com.cn/ai-orchestrator/v1/orgs/513036ab-47e2-41ea-944d-41799585fca5/users/20da2e5d-a78c-418f-b1fa-221542899395/conversations/test-conv-id" \
        // -H "Content-Type: application/json" \
        // -d '[{
        //     "type": "message",
        //     "content": "test message",
        //     "role": "user"
        // }]'

        // 准备测试数据
        String conversationId = "test-conv-id";
        ConvUpdateEvent event = new ConvUpdateEvent();
        event.setType("message");
        event.setContent("test message");
        event.setRole("user");

        // 执行测试
        assertDoesNotThrow(() -> apiClient.updateConversation(conversationId, Arrays.asList(event)));
    }

    @Test
    void testSendQuestion() throws IOException {
        // curl -X POST "https://api-paas-di.yunxuetang.com.cn/ai-orchestrator/v1/orgs/513036ab-47e2-41ea-944d-41799585fca5/users/20da2e5d-a78c-418f-b1fa-221542899395/conversations/test-conv-id/events" \
        // -H "Content-Type: application/json" \
        // -d '{
        //     "content": "test question",
        //     "files": []
        // }'

        // 准备测试数据
        String conversationId = "test-conv-id";
        ConversationEventRequest request = new ConversationEventRequest();
        request.setContent("test question");

        String expectedResponse = "{\"answer\":\"test answer\"}";

        // 执行测试
        String response = apiClient.sendQuestion(conversationId, request);

        // 验证结果
        assertNotNull(response);
    }

    @Test
    void testDeleteConversation() throws IOException {
        // curl -X DELETE "https://api-paas-di.yunxuetang.com.cn/ai-orchestrator/v1/orgs/513036ab-47e2-41ea-944d-41799585fca5/users/20da2e5d-a78c-418f-b1fa-221542899395/conversations/test-conv-id" \
        // -H "Content-Type: application/json"

        // 准备测试数据
        String conversationId = "test-conv-id";

        // 执行测试
        assertDoesNotThrow(() -> apiClient.deleteConversation(conversationId));
    }

    @Test
    void testGetConversationHistory() throws IOException {
        // curl -X GET "https://api-paas-di.yunxuetang.com.cn/ai-orchestrator/v1/orgs/513036ab-47e2-41ea-944d-41799585fca5/users/20da2e5d-a78c-418f-b1fa-221542899395/conversations/test-conv-id/history" \
        // -H "Content-Type: application/json"

        // 准备测试数据
        String conversationId = "test-conv-id";
        ConversationHistoryResponse expectedResponse = new ConversationHistoryResponse();
        ConversationHistoryResponse.Message message = new ConversationHistoryResponse.Message();
        message.setRole("user");
        message.setContent("test question");
        message.setTimestamp("2024-03-21T10:00:00Z");
        expectedResponse.setMessages(Arrays.asList(message));

        ApiResponse<ConversationHistoryResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(0);
        apiResponse.setMessage("success");
        apiResponse.setData(expectedResponse);

        // 执行测试
        ConversationHistoryResponse response = apiClient.getConversationHistory(conversationId);

        // 验证结果
        assertNotNull(response);
        assertNotNull(response.getMessages());
        assertFalse(response.getMessages().isEmpty());
        assertEquals("user", response.getMessages().get(0).getRole());
        assertEquals("test question", response.getMessages().get(0).getContent());
    }
}
