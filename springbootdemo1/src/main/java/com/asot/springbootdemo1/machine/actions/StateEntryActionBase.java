package com.asot.springbootdemo1.machine.actions;

import com.asot.shared.repository.StatusEntityRepository;
import com.asot.shared.machine.enums.Events;
import com.asot.shared.machine.enums.States;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.action.Action;
import reactor.core.publisher.Mono;


public abstract class StateEntryActionBase implements Action<States, Events> {

    public static final String ENTITY = "status_entity";
    protected final Mono<Message<Events>> message = Mono.just(MessageBuilder
            .withPayload(Events.CONTINUE)
            .build());
    protected StatusEntityRepository statusEntityRepository;

    public StateEntryActionBase(StatusEntityRepository statusEntityRepository) {
        this.statusEntityRepository = statusEntityRepository;
    }

}
