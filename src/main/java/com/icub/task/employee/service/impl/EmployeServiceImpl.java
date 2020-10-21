package com.icub.task.employee.service.impl;

import com.icub.task.employee.commons.entity.Employee;
import com.icub.task.employee.commons.event.GeneralInformationEvent;
import com.icub.task.employee.commons.event.ProjectEvent;
import com.icub.task.employee.commons.event.QualificationEvent;
import com.icub.task.employee.commons.exception.EmployeeNotFoundException;
import com.icub.task.employee.commons.exception.UnsupportedEventTypeException;
import com.icub.task.employee.repository.EmployeeRepository;
import com.icub.task.employee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public void generalInformationHandler(GeneralInformationEvent event){
        Employee employee;
        switch (event.getType()){
            case INSERT:
                employee = Employee.builder().generalInformation(event.getGeneralInformation()).build();
                this.insert(employee);
                break;
            case UPDATE:
                employee = findById(event.getEmployeeID());
                employee.setGeneralInformation(event.getGeneralInformation());
                this.insert(employee);
                break;
            case DELETE:
                employee = findById(event.getEmployeeID());
                this.delete(employee.getId());
                break;
            default: new UnsupportedEventTypeException("Unsuported event type. Supported types are INSERT,DELETE,UPDATE");
        }
    }

    @Override
    public void qualificationHandler(QualificationEvent event){
        Employee employee = findById(event.getEmployeeID());
        switch (event.getType()){
            case INSERT:
                if (employee.getQualifications() != null) employee.getQualifications().addAll(event.getQualifications());
                    else employee.setQualifications(event.getQualifications());
                this.insert(employee);
                break;
            case DELETE:
                employee.getQualifications().removeAll(event.getQualifications());
                System.err.println(employee.getQualifications());
                this.insert(employee);
                break;
            default: new UnsupportedEventTypeException("Unsuported event type. Supported types are INSERT,DELETE,UPDATE");
        }
    }

    @Override
    public void projectHandler(ProjectEvent event){
        Employee employee = findById(event.getEmployeeID());
        List<Employee.Project> projects;
        switch (event.getType()){
            case INSERT:
                if (employee.getProjects() != null)
                    employee.getProjects().add(event.getProject());
                else employee.setProjects(Arrays.asList(event.getProject()));
                this.insert(employee);
                break;
            case DELETE:
                projects = employee.getProjects().stream()
                        .filter(p->!p.getProjectID().equals(event.getProject().getProjectID())).collect(Collectors.toList());
                employee.setProjects(projects);
                this.insert(employee);
                break;
            default: new UnsupportedEventTypeException("Unsuported event type. Supported types are INSERT,DELETE,UPDATE");
        }

    }

    @Override
    public Employee insert(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(String id) {
        findById(id);
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee findById(String id) {
        return employeeRepository.findById(id).orElseThrow(()->new EmployeeNotFoundException("Employee with id "+id+" does not exist"));
    }

    @Override
    public List<Employee> list() {
        return employeeRepository.findAll();
    }

    @Override
    public void kafkaCallback(ListenableFuture listenableFuture){
        SuccessCallback<SendResult<Object,Object>> successCallback = result -> log.info("PRODUCED RECORD:  [topic: {}] , [payload:{}]",result.getRecordMetadata().topic(),result.getProducerRecord().value().toString());
        FailureCallback failureCallback = ex -> log.error("Error thrown on producer: ", ex.getLocalizedMessage());
        listenableFuture.addCallback(successCallback,failureCallback);

    }







}
