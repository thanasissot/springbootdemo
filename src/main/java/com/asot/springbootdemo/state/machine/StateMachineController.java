package com.asot.springbootdemo.state.machine;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sm")
@RequiredArgsConstructor
@Log4j2
public class StateMachineController {
    private final StateMachineService stateMachineService;


    @GetMapping
    public ResponseEntity<Void> startStateMachine() {

        stateMachineService.method();

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
