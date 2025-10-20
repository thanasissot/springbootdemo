package com.asot.springbootdemo.state.machine;

import com.asot.springbootdemo.state.machine.actions.ContinueAction;
import com.asot.springbootdemo.state.machine.enums.Events;
import com.asot.springbootdemo.state.machine.enums.States;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class StateMachineConfiguration
    extends EnumStateMachineConfigurerAdapter<States, Events> {

    private final ContinueAction continueAction;

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.INITIAL)
                .stateEntry(States.INITIAL, continueAction)
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
