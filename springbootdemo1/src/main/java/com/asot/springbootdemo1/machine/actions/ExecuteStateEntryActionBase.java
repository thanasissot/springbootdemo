package com.asot.springbootdemo1.machine.actions;

import com.asot.shared.repository.StatusEntityRepository;
import com.asot.shared.machine.enums.Events;
import com.asot.shared.machine.enums.States;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.action.Action;
import reactor.core.publisher.Mono;


public abstract class ExecuteStateEntryActionBase implements Action<States, Events> {

    public static final String ENTITY = "status_entity";
    private final Mono<Message<Events>> message = Mono.just(MessageBuilder
            .withPayload(Events.CONTINUE)
            .build());
    private StatusEntityRepository statusEntityRepository;

    public ExecuteStateEntryActionBase(StatusEntityRepository statusEntityRepository) {
        this.statusEntityRepository = statusEntityRepository;
    }

}
