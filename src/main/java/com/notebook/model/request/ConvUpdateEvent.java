package com.notebook.model.request;

import lombok.Data;
import java.util.List;

@Data
public class ConvUpdateEvent {
    private String type;
    private String content;
    private String role;
    private List<String> files;
} 