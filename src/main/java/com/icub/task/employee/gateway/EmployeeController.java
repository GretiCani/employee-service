package com.icub.task.employee.gateway;

import com.icub.task.employee.commons.entity.Employee;
import com.icub.task.employee.commons.event.EventType;
import com.icub.task.employee.commons.event.GeneralInformationEvent;
import com.icub.task.employee.commons.event.ProjectEvent;
import com.icub.task.employee.commons.event.QualificationEvent;
import com.icub.task.employee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Component
@RequestMapping("/api/employee")
@Slf4j @Validated
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private KafkaTemplate<Object,Object> kafkaTemplate;


    @Value("${icub.kafka.topic.general-information}")
    private String generalInformationTopic;

    @Value("${icub.kafka.topic.qualifation}")
    private String qualificationTopic;

    @Value("${icub.kafka.topic.project}")
    private String projetTopic;


    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees(){
        return ResponseEntity.ok(employeeService.list());
    }

    @PostMapping("/general-information")
    public ResponseEntity<Void> insertGeneralInformation( EventType type, @Valid @RequestBody  Employee.GeneralInformation request){
        this.employeeService.kafkaCallback(kafkaTemplate.send(generalInformationTopic,GeneralInformationEvent.builder()
                .type(type).generalInformation(request).build()));
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/general-information")
    public ResponseEntity<Void> updateGeneralInformation(EventType type, @PathVariable String id,@Valid @RequestBody Employee.GeneralInformation request){
        this.employeeService.findById(id);
        this.employeeService.kafkaCallback(kafkaTemplate.send(generalInformationTopic,GeneralInformationEvent.builder()
                .type(type).employeeID(id).generalInformation(request).build()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/general-information")
    public ResponseEntity<Void> deleteGeneralInformation(EventType type, @PathVariable String id){
        this.employeeService.findById(id);
        this.employeeService.kafkaCallback(kafkaTemplate.send(generalInformationTopic,GeneralInformationEvent.builder()
                .type(type).employeeID(id).build()));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/qualification")
    public ResponseEntity<Void> createQualification(EventType type, @PathVariable String id, @RequestBody List<String> request){
        this.employeeService.findById(id);
        this.employeeService.kafkaCallback(this.kafkaTemplate.send(qualificationTopic, QualificationEvent.builder()
                .type(type).employeeID(id).qualifications(request).build()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/project")
    public ResponseEntity<Void> insertProject(EventType type, @PathVariable String id,@Valid @RequestBody Employee.Project request){
        this.employeeService.findById(id);
        this.employeeService.kafkaCallback(this.kafkaTemplate.send(projetTopic, ProjectEvent.builder()
                .type(type).employeeID(id).project(request).build()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/project/{projectID}")
    public ResponseEntity<Void> deleteProject(EventType type, @PathVariable String id,@PathVariable String projectID){
        this.employeeService.findById(id);
        this.employeeService.kafkaCallback(this.kafkaTemplate.send(projetTopic,ProjectEvent.builder()
                .type(type).employeeID(id).project(Employee.Project.builder()
                        .projectID(projectID).build()).build()));
        return ResponseEntity.noContent().build();
    }


}
