package com.asot.springbootdemo.service;

import com.asot.springbootdemo.repository.TestEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Log4j2
public class RetryService {

    private final TestEntityRepository testEntityRepository;
    private final Random random = new Random();

    @Retryable(retryFor = IllegalArgumentException.class, maxAttempts = 5, backoff = @Backoff(delay = 1000))
    public int retryableMethod() throws IllegalArgumentException {
        log.info("retryableMethod executed");

        int maxId = testEntityRepository.findMaxId().intValue();
        int randomId = random.nextInt((int)(maxId * 1.5));

        if (!(randomId == maxId)) {
            log.error("randomId is " + randomId);
            throw new IllegalArgumentException(String.format("randomId is wrong = %d. retry", randomId));
        }

        log.info("retryableMethod executed successfully");
        return maxId;
    }

}
