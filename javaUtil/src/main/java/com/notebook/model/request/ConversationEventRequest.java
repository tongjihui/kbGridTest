package com.notebook.model.request;

import lombok.Data;

import java.util.List;

@Data
public class ConversationEventRequest {
    private String content;
    private List<String> files;
} 