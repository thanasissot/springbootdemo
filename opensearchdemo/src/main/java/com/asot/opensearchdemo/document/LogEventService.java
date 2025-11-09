package com.asot.opensearchdemo.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LogEventService {
    private final RestHighLevelClient client;
    private final ObjectMapper mapper;
    private static final String INDEX = "application-logs";

    // Pattern to parse log entries
    private static final Pattern LOG_PATTERN = Pattern.compile(
            "^(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}) \\[([^\\]]+)\\] (\\w+)\\s*:\\s*(.*)$"
    );

    public LogEventService(RestHighLevelClient client) {
        this.client = client;
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    public String indexLogEvent(LogEvent logEvent) throws Exception {
        logEvent.setId(UUID.randomUUID().toString());

        // Set nested log data
        LogEvent.LogData logData = new LogEvent.LogData();
        logData.setLevel(logEvent.getLogLevel());
        logData.setCategory(extractLogCategory(logEvent.getMessage()));
        logData.setComponent(extractComponent(logEvent.getMessage()));
        logEvent.setData(logData);

        IndexRequest request = new IndexRequest(INDEX)
                .id(logEvent.getId())
                .source(mapper.writeValueAsString(logEvent), org.opensearch.common.xcontent.XContentType.JSON);

        client.index(request, RequestOptions.DEFAULT);
        return logEvent.getId();
    }

    public String indexLogEntry(String logLine) throws Exception {
        LogEvent logEntry = parseLogEntry(logLine);
        return indexLogEvent(logEntry);
    }

    private LogEvent parseLogEntry(String logLine) {
        Matcher matcher = LOG_PATTERN.matcher(logLine);

        if (matcher.matches()) {
            LogEvent logEvent = new LogEvent();
            logEvent.setTimestamp(LocalDateTime.parse(matcher.group(1),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            logEvent.setThread(matcher.group(2));
            logEvent.setLogLevel(matcher.group(3));
            logEvent.setMessage(matcher.group(4));
            logEvent.setLogger(extractLogger(logEvent.getMessage()));
            logEvent.setApplication("springboot-app");
            logEvent.setEnvironment("development");

            return logEvent;
        }

        // Fallback for unparseable logs
        LogEvent logEvent = new LogEvent();
        logEvent.setTimestamp(LocalDateTime.now());
        logEvent.setLogLevel("UNKNOWN");
        logEvent.setMessage(logLine);
        logEvent.setApplication("springboot-app");
        logEvent.setEnvironment("development");

        return logEvent;
    }

    private String extractLogCategory(String message) {
        if (message.contains("Hibernate") || message.contains("HHH")) {
            return "database";
        } else if (message.contains("Tomcat") || message.contains("HTTP")) {
            return "web-server";
        } else if (message.contains("Spring") || message.contains("ApplicationContext")) {
            return "framework";
        } else if (message.contains("HikariPool")) {
            return "connection-pool";
        }
        return "application";
    }

    private String extractComponent(String message) {
        if (message.contains("HikariPool")) {
            return "hikari-cp";
        } else if (message.contains("Hibernate")) {
            return "hibernate";
        } else if (message.contains("Tomcat")) {
            return "tomcat";
        } else if (message.contains("Spring")) {
            return "spring-framework";
        }
        return "unknown";
    }

    private String extractLogger(String message) {
        // Extract logger name from message if present
        if (message.contains("::")) {
            return message.split("::")[0];
        }
        return "root";
    }
}
