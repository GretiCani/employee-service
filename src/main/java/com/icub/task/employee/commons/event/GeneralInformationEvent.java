package com.icub.task.employee.commons.event;

import com.icub.task.employee.commons.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneralInformationEvent {

    private EventType type;
    private String employeeID;
    private Employee.GeneralInformation generalInformation;
}
