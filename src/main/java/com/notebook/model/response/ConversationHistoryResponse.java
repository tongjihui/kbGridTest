package com.notebook.model.response;

import lombok.Data;
import java.util.List;

@Data
public class ConversationHistoryResponse {
    private List<Message> messages;

    @Data
    public static class Message {
        private String role;
        private String content;
        private String timestamp;
    }
} 