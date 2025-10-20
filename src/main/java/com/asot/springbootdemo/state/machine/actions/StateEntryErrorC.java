package com.asot.springbootdemo.state.machine.actions;

import com.asot.springbootdemo.model.StatusEntity;
import com.asot.springbootdemo.repository.StatusEntityRepository;
import com.asot.springbootdemo.state.machine.enums.Events;
import com.asot.springbootdemo.state.machine.enums.States;
import org.springframework.statemachine.StateContext;

import java.time.LocalDateTime;

public class StateEntryErrorC extends StateEntryActionBase {

    private final States state;

    public StateEntryErrorC(StatusEntityRepository statusEntityRepository, States state) {
        super(statusEntityRepository);
        this.state = state;
    }

    @Override
    public void execute(StateContext<States, Events> context) {
        LocalDateTime now = LocalDateTime.now();
        StatusEntity statusEntity = context.getExtendedState().get(ENTITY, StatusEntity.class);
        statusEntity.setStateC(state);
        statusEntity.setLatestStateCErrorTs(now);
        statusEntity.setLastUpdatedTs(now);

        StatusEntity savedStatusEntity = statusEntityRepository.save(statusEntity);
        context.getExtendedState().getVariables().put(ENTITY, savedStatusEntity);
        context.getStateMachine().sendEvent(message).subscribe();
    }
}
