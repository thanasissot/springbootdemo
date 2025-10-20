package com.asot.springbootdemo.state.machine;

import com.asot.springbootdemo.model.StatusEntity;
import com.asot.springbootdemo.repository.StatusEntityRepository;
import com.asot.springbootdemo.state.machine.enums.Events;
import com.asot.springbootdemo.state.machine.enums.States;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.asot.springbootdemo.state.machine.StateMachineConfiguration.ENTITY;

@Service
@RequiredArgsConstructor
public class StateMachineService {

    private final StatusEntityRepository statusEntityRepository;
    private final StateMachineFactory<States, Events> factory;
    private List<StateMachine<States, Events>> stateMachines = new ArrayList<>();
    private final Mono<Message<Events>> message = Mono.just(MessageBuilder
            .withPayload(Events.CONTINUE)
            .build());


    public void method() {
        // Create and save new entity
        StatusEntity entity = new StatusEntity();
        entity.setCreatedTs(LocalDateTime.now());
        entity.setCurrentState(States.RUNNING);
        entity.setLastUpdatedTs(LocalDateTime.now());
        StatusEntity savedEntity = statusEntityRepository.save(entity);

        StateMachine<States,Events> stateMachine = factory.getStateMachine();
        stateMachine.addStateListener(new StateMachineEventListener());

        stateMachine.getExtendedState().getVariables().put(ENTITY, savedEntity);

        stateMachine.startReactively().subscribe();
        stateMachines.add(stateMachine);
    }

    public void manuallySentContinueEvent() {
        // send continue event to the rest
        stateMachines.forEach(
                sm -> sm.sendEvent(message).subscribe()
        );
    }

    @Scheduled(cron = "${cron.expression.send.continue.event.schedule}")
    void sendContinueEvent() {
        // remove state machines that are finished / not running anymore
        stateMachines = stateMachines.stream().filter(sm -> !sm.isComplete()).collect(Collectors.toList());
        // send continue event to the rest
        stateMachines.forEach(
            sm -> sm.sendEvent(message).subscribe()
        );

    }

}
