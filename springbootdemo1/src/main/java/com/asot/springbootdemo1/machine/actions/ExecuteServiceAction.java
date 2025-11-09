package com.asot.springbootdemo1.machine.actions;

import com.asot.springbootdemo1.machine.ExecuteRequestsService;
import com.asot.shared.machine.enums.Events;
import com.asot.shared.machine.enums.ServiceStatus;
import com.asot.shared.machine.enums.States;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Log4j2
public class ExecuteServiceAction implements Action<States, Events> {

    private final ExecuteRequestsService executeRequestsService;

    @Override
    public void execute(StateContext<States, Events> context) {

        ServiceStatus serviceStatus = Objects.requireNonNull(executeRequestsService.getServiceStatusResponse().block()).getStatus();
        log.info("Service status: " + serviceStatus);
        Message<Events> message = MessageBuilder
                .withPayload(Events.fromServiceStatus(serviceStatus))
                .build();
        context.getStateMachine().sendEvent(Mono.just(message)).subscribe();
    }
}
