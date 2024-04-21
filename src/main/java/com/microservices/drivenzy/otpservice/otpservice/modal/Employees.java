package com.microservices.drivenzy.otpservice.otpservice.modal;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Document("employees")
public class Employees {
    @Transient
    public static final String SEQUENCE_NAME = "employee_sequence";

    @Id
    private String id;
    private String empid;
    private String name;
    private String email;
    private String department;
    private String designation;
    private String dob;
    private String doj;
    private String address;
    private String mobileno;
    private String userId;
    private List<EmployeeExperience> employeeExperience;
    private Map<String,Double> skillVsRating;

}
