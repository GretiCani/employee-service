package com.icub.task.employee.service;


import com.icub.task.employee.commons.entity.Employee;
import com.icub.task.employee.commons.event.GeneralInformationEvent;
import com.icub.task.employee.commons.event.ProjectEvent;
import com.icub.task.employee.commons.event.QualificationEvent;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;

public interface EmployeeService {
    Employee insert(Employee employee);
    void delete(String id);
    Employee findById(String id);
    List<Employee> list();

    void generalInformationHandler(GeneralInformationEvent event);
    void qualificationHandler(QualificationEvent event);
    void projectHandler(ProjectEvent event);

    void kafkaCallback(ListenableFuture listenableFuture);

}
