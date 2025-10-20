package com.asot.springbootdemo.state.machine.actions;

import com.asot.springbootdemo.state.machine.enums.Events;
import com.asot.springbootdemo.state.machine.enums.States;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ContinueAction implements Action<States, Events> {
    @Override
    public void execute(StateContext<States, Events> context) {

        Message<Events> message = MessageBuilder
                .withPayload(Events.CONTINUE)
                .build();
        context.getStateMachine().sendEvent(Mono.just(message)).subscribe();
    }
}
