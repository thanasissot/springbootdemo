package com.asot.opensearchdemo.document;

import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.index.query.BoolQueryBuilder;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.index.query.RangeQueryBuilder;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.opensearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {

    private final RestHighLevelClient client;

    public SearchService(RestHighLevelClient client) {
        this.client = client;
    }

    public List<Map<String, Object>> searchByKeyword(String keyword) throws Exception {
        SearchRequest request = new SearchRequest("application-logs");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.multiMatchQuery(keyword, "message", "logger", "thread"))
                .sort("timestamp", SortOrder.DESC)
                .size(100);
        request.source(sourceBuilder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        List<Map<String, Object>> results = new ArrayList<>();
        response.getHits().forEach(hit -> results.add(hit.getSourceAsMap()));
        return results;
    }

    public List<Map<String, Object>> searchByLogLevel(String logLevel) throws Exception {
        SearchRequest request = new SearchRequest("application-logs");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.termQuery("data.log.level", logLevel.toLowerCase()))
                .sort("timestamp", SortOrder.DESC);
        request.source(sourceBuilder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        List<Map<String, Object>> results = new ArrayList<>();
        response.getHits().forEach(hit -> results.add(hit.getSourceAsMap()));
        return results;
    }

    public List<Map<String, Object>> searchByTimeRange(String startTime, String endTime,
                                                       String logLevel, String keyword) throws Exception {
        SearchRequest request = new SearchRequest("application-logs");

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // Time range filter
        if (startTime != null && endTime != null) {
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("timestamp")
                    .gte(startTime)
                    .lte(endTime);
            boolQuery.filter(rangeQuery);
        }

        // Log level filter
        if (logLevel != null && !logLevel.isEmpty()) {
            boolQuery.filter(QueryBuilders.termQuery("logLevel", logLevel));
        }

        // Keyword search
        if (keyword != null && !keyword.isEmpty()) {
            boolQuery.must(QueryBuilders.multiMatchQuery(keyword, "message", "logger"));
        }

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(boolQuery)
                .sort("timestamp", SortOrder.DESC)
                .size(1000);
        request.source(sourceBuilder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        List<Map<String, Object>> results = new ArrayList<>();
        response.getHits().forEach(hit -> results.add(hit.getSourceAsMap()));
        return results;
    }
}
