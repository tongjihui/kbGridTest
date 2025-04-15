package com.notebook.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "notebook.api")
public class ApiConfig {
    private String baseUrl;
    private String orgId;
    private String userId;
} 