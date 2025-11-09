package com.asot.springbootdemo1.machine.actions;

import com.asot.shared.model.StatusEntity;
import com.asot.shared.repository.StatusEntityRepository;
import com.asot.shared.machine.enums.Events;
import com.asot.shared.machine.enums.States;
import org.springframework.statemachine.StateContext;

import java.time.LocalDateTime;

public class StateEntryOkB extends StateEntryActionBase {

    private final States state;

    public StateEntryOkB(StatusEntityRepository statusEntityRepository, States state) {
        super(statusEntityRepository);
        this.state = state;
    }

    @Override
    public void execute(StateContext<States, Events> context) {
        LocalDateTime now = LocalDateTime.now();
        StatusEntity statusEntity = context.getExtendedState().get(ENTITY, StatusEntity.class);
        statusEntity.setStateB(state);
        statusEntity.setPassedStateBTs(now);
        statusEntity.setLastUpdatedTs(now);

        StatusEntity savedStatusEntity = statusEntityRepository.save(statusEntity);
        context.getExtendedState().getVariables().put(ENTITY, savedStatusEntity);
        context.getStateMachine().sendEvent(message).subscribe();
    }
}
