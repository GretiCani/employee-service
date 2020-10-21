package com.icub.task.employee.commons.event;

import com.icub.task.employee.commons.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEvent {
    private EventType type;
    @NotEmpty(message = "employee id is required")
    private String employeeID;
    private Employee.Project project;
}
