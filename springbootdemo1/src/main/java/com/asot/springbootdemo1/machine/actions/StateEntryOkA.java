package com.asot.springbootdemo1.machine.actions;

import com.asot.springbootdemo1.model.StatusEntity;
import com.asot.springbootdemo1.repository.StatusEntityRepository;
import com.asot.springbootdemo1.machine.enums.Events;
import com.asot.springbootdemo1.machine.enums.States;
import org.springframework.statemachine.StateContext;

import java.time.LocalDateTime;

public class StateEntryOkA extends StateEntryActionBase {

    private final States state;

    public StateEntryOkA(StatusEntityRepository statusEntityRepository, States state) {
        super(statusEntityRepository);
        this.state = state;
    }

    @Override
    public void execute(StateContext<States, Events> context) {
        LocalDateTime now = LocalDateTime.now();
        StatusEntity statusEntity = context.getExtendedState().get(ENTITY, StatusEntity.class);
        statusEntity.setStateA(state);
        statusEntity.setPassedStateATs(now);
        statusEntity.setLastUpdatedTs(now);

        StatusEntity savedStatusEntity = statusEntityRepository.save(statusEntity);
        context.getExtendedState().getVariables().put(ENTITY, savedStatusEntity);
        context.getStateMachine().sendEvent(message).subscribe();
    }
}
