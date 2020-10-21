package com.icub.task.employee.listener;

import com.icub.task.employee.commons.event.GeneralInformationEvent;
import com.icub.task.employee.commons.event.ProjectEvent;
import com.icub.task.employee.commons.event.QualificationEvent;
import com.icub.task.employee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(id = "employee-topic-groups",topics = {"employee-general-information-topic",
        "employee-qualification-topic","employee-project-topic"})
public class EmployeeListener {

    @Autowired
    private EmployeeService employeeService;

    @KafkaHandler
    public void writeToGeneralInformationTopic(GeneralInformationEvent event){
        log.info("CONSUMER GENERAL: [topic: {}], [payload: {}]","employee-general-information-topic",event);
        employeeService.generalInformationHandler(event);
    }

    @KafkaHandler
    public void writeToQualificationTopic(QualificationEvent event){
        log.info("CONSUMER QUALIFICATION: [topic: {}], [payload: {}]","employee-qualification-topic",event);
        employeeService.qualificationHandler(event);

    }

    @KafkaHandler
    public void writeToProjectTopic(ProjectEvent event){
        log.info("CONSUMER: [topic: {}], [payload: {}]","employee-project-topic",event);
        employeeService.projectHandler(event);

    }

    @KafkaHandler(isDefault = true)
    public void unsupportedType(Object request){
        log.info("REJECTED MESSAGE: unsupported type {}",request);

    }
}
