package com.asot.springbootdemo1.machine.enums;

public enum Events {
    INIT,
    CONTINUE,
    OK,
    WARN,
    ERROR,
    PERMA_FAILURE;


    public static Events fromServiceStatus(ServiceStatus status) {
        return Events.valueOf(status.name());
    }
}
