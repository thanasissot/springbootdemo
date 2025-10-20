package com.asot.springbootdemo.state.machine;

import com.asot.springbootdemo.state.machine.enums.Events;
import com.asot.springbootdemo.state.machine.enums.States;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StateMachineService {

    private final StateMachineFactory<States, Events> factory;

    public void method() {
        StateMachine<States,Events> stateMachine = factory.getStateMachine();
        stateMachine.addStateListener(new StateMachineEventListener());
        stateMachine.startReactively().subscribe();
    }

}
