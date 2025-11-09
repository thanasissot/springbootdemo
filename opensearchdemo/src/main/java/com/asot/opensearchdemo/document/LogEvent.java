package com.asot.opensearchdemo.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogEvent {
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private String thread;

    private String logLevel;

    private String message;

    private String logger;

    // Additional fields for better filtering and analysis
    private String application;

    private String environment;

    // Nested object for structured log data
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LogData {
        private String level;
        private String category;
        private String component;
    }

    private LogData data;
}
