package com.asot.springbootdemo1.machine;

import com.asot.springbootdemo1.machine.actions.*;
import com.asot.shared.machine.enums.Events;
import com.asot.shared.machine.enums.States;
import com.asot.shared.model.StatusEntity;
import com.asot.shared.repository.StatusEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class StateMachineConfiguration
    extends EnumStateMachineConfigurerAdapter<States, Events> {

    public static final String ENTITY = "status_entity";
    private final StatusEntityRepository statusEntityRepository;
    private final ContinueAction continueAction;
    private final ExecuteServiceAction executeServiceAction;
    private final Mono<Message<Events>> message = Mono.just(MessageBuilder
            .withPayload(Events.CONTINUE)
            .build());

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.INITIAL)
                .stateEntry(States.INITIAL, continueAction)
                .stateEntry(States.PERMANENT_FAILURE, continueAction)
                .stateEntry(States.FINISHED, context -> {
                    LocalDateTime now = LocalDateTime.now();
                    StatusEntity statusEntity = context.getExtendedState().get(ENTITY, StatusEntity.class);
                    statusEntity.setCurrentState(States.FINISHED);
                    statusEntity.setLastUpdatedTs(now);
                    StatusEntity savedStatusEntity = statusEntityRepository.save(statusEntity);
                })
                .stateEntry(States.SERVICE_A_EXECUTING, executeServiceAction)
                .stateEntry(States.SERVICE_B_EXECUTING, executeServiceAction)
                .stateEntry(States.SERVICE_C_EXECUTING, executeServiceAction)

                .stateEntry(States.SERVICE_A_OK, new StateEntryOkA(statusEntityRepository, States.SERVICE_A_OK))
                .stateEntry(States.SERVICE_A_WARN, new StateEntryOkA(statusEntityRepository, States.SERVICE_A_WARN))
                .stateEntry(States.SERVICE_B_OK, new StateEntryOkB(statusEntityRepository, States.SERVICE_B_OK))
                .stateEntry(States.SERVICE_B_WARN, new StateEntryOkB(statusEntityRepository, States.SERVICE_B_WARN))
                .stateEntry(States.SERVICE_C_OK, new StateEntryOkC(statusEntityRepository, States.SERVICE_C_OK))
                .stateEntry(States.SERVICE_C_WARN, new StateEntryOkC(statusEntityRepository, States.SERVICE_C_WARN))
                .stateEntry(States.SERVICE_A_ERROR, new StateEntryErrorA(statusEntityRepository, States.SERVICE_A_ERROR))
                .stateEntry(States.SERVICE_B_ERROR, new StateEntryErrorB(statusEntityRepository, States.SERVICE_B_ERROR))
                .stateEntry(States.SERVICE_C_ERROR, new StateEntryErrorC(statusEntityRepository, States.SERVICE_C_ERROR))

                .states(EnumSet.allOf(States.class))
                .end(States.FINISHED);
    }


    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(States.INITIAL)
                .target(States.SERVICE_A_EXECUTING)
                .event(Events.CONTINUE)
                .action(continueAction)
                .and()
                .withExternal()
                .source(States.SERVICE_A_EXECUTING)
                .target(States.SERVICE_A_OK)
                .event(Events.OK)
                .and()
                .withExternal()
                .source(States.SERVICE_A_EXECUTING)
                .target(States.SERVICE_A_WARN)
                .event(Events.WARN)
                .and()
                .withExternal()
                .source(States.SERVICE_A_EXECUTING)
                .target(States.SERVICE_A_ERROR)
                .event(Events.ERROR)
                .and()
                .withExternal()
                .source(States.SERVICE_A_OK)
                .target(States.SERVICE_B_EXECUTING)
                .event(Events.CONTINUE)
                .and()
                .withExternal()
                .source(States.SERVICE_A_WARN)
                .target(States.SERVICE_B_EXECUTING)
                .event(Events.CONTINUE)
                .and()
                .withExternal()
                .source(States.SERVICE_A_ERROR)
                .target(States.SERVICE_A_WAITING_FOR_RETRY)
                .event(Events.CONTINUE)
                .and()
                .withExternal()
                .source(States.SERVICE_A_WAITING_FOR_RETRY)
                .target(States.SERVICE_A_EXECUTING)
                .event(Events.CONTINUE)
                .and()
                .withExternal()
                .source(States.SERVICE_A_WAITING_FOR_RETRY)
                .target(States.SERVICE_A_PERMANENT_FAILURE)
                .event(Events.PERMA_FAILURE)
                .and()
                .withExternal()
                .source(States.SERVICE_A_PERMANENT_FAILURE)
                .target(States.PERMANENT_FAILURE)
                .event(Events.CONTINUE)


                // SERVICE B
                .and()
                .withExternal()
                .source(States.SERVICE_B_EXECUTING)
                .target(States.SERVICE_B_OK)
                .event(Events.OK)
                .and()
                .withExternal()
                .source(States.SERVICE_B_EXECUTING)
                .target(States.SERVICE_B_WARN)
                .event(Events.WARN)
                .and()
                .withExternal()
                .source(States.SERVICE_B_EXECUTING)
                .target(States.SERVICE_B_ERROR)
                .event(Events.ERROR)
                .and()
                .withExternal()
                .source(States.SERVICE_B_OK)
                .target(States.SERVICE_C_EXECUTING)
                .event(Events.CONTINUE)
                .and()
                .withExternal()
                .source(States.SERVICE_B_WARN)
                .target(States.SERVICE_C_EXECUTING)
                .event(Events.CONTINUE)
                .and()
                .withExternal()
                .source(States.SERVICE_B_ERROR)
                .target(States.SERVICE_B_WAITING_FOR_RETRY)
                .event(Events.CONTINUE)
                .and()
                .withExternal()
                .source(States.SERVICE_B_WAITING_FOR_RETRY)
                .target(States.SERVICE_B_EXECUTING)
                .event(Events.CONTINUE)
                .and()
                .withExternal()
                .source(States.SERVICE_B_WAITING_FOR_RETRY)
                .target(States.SERVICE_B_PERMANENT_FAILURE)
                .event(Events.PERMA_FAILURE)
                .and()
                .withExternal()
                .source(States.SERVICE_B_PERMANENT_FAILURE)
                .target(States.PERMANENT_FAILURE)
                .event(Events.CONTINUE)

                // SERVICE C
                .and()
                .withExternal()
                .source(States.SERVICE_C_EXECUTING)
                .target(States.SERVICE_C_OK)
                .event(Events.OK)
                .and()
                .withExternal()
                .source(States.SERVICE_C_EXECUTING)
                .target(States.SERVICE_C_WARN)
                .event(Events.WARN)
                .and()
                .withExternal()
                .source(States.SERVICE_C_EXECUTING)
                .target(States.SERVICE_C_ERROR)
                .event(Events.ERROR)
                .and()
                .withExternal()
                .source(States.SERVICE_C_OK)
                .target(States.FINISHED)
                .event(Events.CONTINUE)
                .and()
                .withExternal()
                .source(States.SERVICE_C_WARN)
                .target(States.FINISHED)
                .event(Events.CONTINUE)
                .and()
                .withExternal()
                .source(States.SERVICE_C_ERROR)
                .target(States.SERVICE_C_WAITING_FOR_RETRY)
                .event(Events.CONTINUE)
                .and()
                .withExternal()
                .source(States.SERVICE_C_WAITING_FOR_RETRY)
                .target(States.SERVICE_C_EXECUTING)
                .event(Events.CONTINUE)
                .and()
                .withExternal()
                .source(States.SERVICE_C_WAITING_FOR_RETRY)
                .target(States.SERVICE_C_PERMANENT_FAILURE)
                .event(Events.PERMA_FAILURE)
                .and()
                .withExternal()
                .source(States.SERVICE_C_PERMANENT_FAILURE)
                .target(States.PERMANENT_FAILURE)
                .event(Events.CONTINUE)

                .and()
                .withExternal()
                .source(States.PERMANENT_FAILURE)
                .target(States.FINISHED)
                .event(Events.CONTINUE);

    }
}
