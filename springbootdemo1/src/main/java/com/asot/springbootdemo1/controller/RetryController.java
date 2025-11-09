package com.asot.springbootdemo1.controller;

import com.asot.springbootdemo1.service.RetryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/retry")
@RequiredArgsConstructor
@Log4j2
public class RetryController {
    private final RetryService retryService;


    @GetMapping
    public ResponseEntity<Integer> callRetryableServiceMethod() {

        try {
            int a = retryService.retryableMethod();
            return ResponseEntity.ok(a);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
