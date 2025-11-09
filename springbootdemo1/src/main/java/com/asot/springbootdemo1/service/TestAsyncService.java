package com.asot.springbootdemo1.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
public class TestAsyncService {

    public CompletableFuture<List<String>> testAsync() {
        String url = "http://localhost:8280/castlemock/mock/rest/project/B4ljt6/application/BxFiso/test";
        HttpClient httpClient = HttpClient.newHttpClient();

        long startNano = System.nanoTime();

        List<CompletableFuture<String>> futures = Stream.of("a", "b", "c", "d", "e", "f", "g", "h")
                .map(letter -> {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .timeout(Duration.ofSeconds(10))
                            .GET()
                            .build();

                    return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                            .thenApply(res -> letter + ":responseLetter:" + res.body())
                            .exceptionally(ex -> {
                                log.warn("Request for {} failed: {}", letter, ex.toString());
                                return letter + ":error:" + ex.getMessage();
                            });
                })
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        CompletableFuture<Void> all = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return all.thenApply(v -> {
            long elapsedNano = System.nanoTime() - startNano;
            long elapsedMillis = TimeUnit.NANOSECONDS.toMillis(elapsedNano);
            log.info("All requests completed in {} ms ({} ns)", elapsedMillis, elapsedNano);
            return futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
        });

    }

}
