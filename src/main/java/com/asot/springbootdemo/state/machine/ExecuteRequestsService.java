package com.asot.springbootdemo.state.machine;

import com.asot.springbootdemo.service.HttpClientService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ExecuteRequestsService {

    private final HttpClientService httpClient;
    private final String baseUrl = "http://localhost:8280/castlemock/mock/rest/project/W1Kwl3/application/NaMg6S/servicea";

    public ExecuteRequestsService(HttpClientService httpClient) {
        this.httpClient = httpClient;
    }

    public Mono<ServiceStatusResponse> getServiceStatusResponse() {
        return httpClient.get(baseUrl, ServiceStatusResponse.class);
    }

}
