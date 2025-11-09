package com.asot.opensearchdemo.document;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logs")
public class LogEventController {

    private final LogEventService logEventService;
    private final SearchService searchService;

    public LogEventController(LogEventService logEventService, SearchService searchService) {
        this.logEventService = logEventService;
        this.searchService = searchService;
    }

    @PostMapping
    public String addLogEntry(@RequestBody LogEvent logEvent) throws Exception {
        return logEventService.indexLogEvent(logEvent);
    }

    @PostMapping("/parse")
    public String addLogLine(@RequestBody String logLine) throws Exception {
        return logEventService.indexLogEntry(logLine);
    }

    @GetMapping("/search")
    public List<Map<String, Object>> search(@RequestParam String keyword) throws Exception {
        return searchService.searchByKeyword(keyword);
    }

    @GetMapping("/search/level")
    public List<Map<String, Object>> searchByLevel(@RequestParam String logLevel) throws Exception {
        return searchService.searchByLogLevel(logLevel);
    }

    @GetMapping("/search/advanced")
    public List<Map<String, Object>> advancedSearch(
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(required = false) String logLevel,
            @RequestParam(required = false) String keyword) throws Exception {
        return searchService.searchByTimeRange(startTime, endTime, logLevel, keyword);
    }
}
