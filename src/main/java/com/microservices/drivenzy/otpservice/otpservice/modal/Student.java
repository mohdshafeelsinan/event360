package com.microservices.drivenzy.otpservice.otpservice.modal;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Student")
public class Student {
    private String firstName;
    private String lastName;
    private Long mobile;
    private Long whatsappNo;
    private String email;
    private String socialMedia;
    private String studentId;
    private String emiritesOrPassportNo;
    private String healthIssue;
    private String dob;
    private String gender;
    private String image;
    private String address;
    private Boolean status;
}
