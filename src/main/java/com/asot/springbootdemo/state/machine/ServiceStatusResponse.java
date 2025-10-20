package com.asot.springbootdemo.state.machine;

import com.asot.springbootdemo.state.machine.enums.ServiceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceStatusResponse {

    private ServiceStatus status;

}
