package com.asot.springbootdemo1.machine;

import com.asot.springbootdemo1.machine.enums.ServiceStatus;
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
