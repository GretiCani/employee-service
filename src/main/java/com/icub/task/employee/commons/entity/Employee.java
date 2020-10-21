package com.icub.task.employee.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Document
public class Employee {

    @Id
    private String id;
    private GeneralInformation generalInformation;
    private List<String> qualifications;
    private List<Project> projects;

    @Data @Builder @AllArgsConstructor@NoArgsConstructor
    public static class GeneralInformation {
        @NotEmpty(message = "passportID is required")
        private String passportID;
        @NotEmpty(message = "name is required")
        private String name;
        @NotEmpty(message = "name is required")
        private String surname;
        @Email(message = "invalid email format")
        private String email;
        private int age;
    }

    @Data @Builder @AllArgsConstructor@NoArgsConstructor
    public static class Qualification{
        @NotEmpty(message = "qualificationID is required")
        private String qualificationID;
        private String digitalProof;
        private String description;
    }

    @Data @Builder @AllArgsConstructor@NoArgsConstructor
    public static class Project{
        @NotEmpty(message = "projectID is required")
        private String projectID;
        @NotEmpty(message = "project name is required")
        private String name;
        @NotEmpty(message = "teamleader is required")
        private String teamleader;
        private List<String> frontDevelopers;
        private List<String> endDevelopers;
        private String description;
        private String startDate;


    }


}
