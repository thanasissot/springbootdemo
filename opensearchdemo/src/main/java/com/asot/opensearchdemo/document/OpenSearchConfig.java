package com.asot.opensearchdemo.document;

import org.apache.hc.core5.http.HttpHost;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenSearchConfig {

    @Bean
    public RestHighLevelClient openSearchClient() {
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost("http", "localhost", 9200))
        );
    }
}
