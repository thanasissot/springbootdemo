package com.asot.springbootdemo.controller;

import com.asot.springbootdemo.service.TestAsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController()
@RequestMapping("/async")
@RequiredArgsConstructor
public class TestAsyncController {

    private final TestAsyncService testAsyncService;

    @GetMapping
    public CompletableFuture<String> testAsync() {
        return testAsyncService.testAsync()
                .thenApply(list -> String.join("-||-", list));

    }

}
