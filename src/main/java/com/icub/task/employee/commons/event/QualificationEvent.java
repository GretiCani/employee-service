package com.icub.task.employee.commons.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QualificationEvent {
    private EventType type;
    @NotEmpty(message = "employee id is required")
    private String employeeID;
    private List<String> qualifications;
}
