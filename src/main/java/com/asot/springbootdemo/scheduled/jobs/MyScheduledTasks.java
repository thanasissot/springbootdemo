package com.asot.springbootdemo.scheduled.jobs;

import com.asot.springbootdemo.model.TestEntity;
import com.asot.springbootdemo.repository.TestEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class MyScheduledTasks {

    private final TestEntityRepository testEntityRepository;


    private static int fixedRateCounter = 0;
    @Scheduled(fixedRate = 5000) // Run every 5 seconds
    public void logMessageEvery5Seconds() {
        log.info("MyScheduledTasks::logMessageEvery5Seconds - executed {} times", fixedRateCounter++);
    }

    @Scheduled(cron = "${cron.expression}")
    public void runBasedOnDynamicCronExpression() {
        long nextId = testEntityRepository.findMaxId() + 1;
        TestEntity newTestEntity = TestEntity.builder()
                .name(String.valueOf(nextId).concat("-testEntity"))
                .email("".concat(String.valueOf((char) (96 + nextId))).concat("@mail.com"))
                .build();

        newTestEntity = testEntityRepository.save(newTestEntity);
        log.info("10 seconds passed. Created new testEntity: {}", newTestEntity.getId());
    }

}
