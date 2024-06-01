package com.microservices.drivenzy.otpservice.otpservice.modal;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

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
    private String emiratesOrPassportNo;
    private String healthIssue;
    private String gender;
    private String image;
    private String address;
    private Boolean status;
    private Long phoneNumber;
    private Long whatsappNumber;
    private String id;
    private LocalDate dob; // Using LocalDate for date of birth
    private List<String> couresEnrolled;
    private List<String> classDate; // Assuming these are dates, using List<String> for simplicity
    private List<String> trainer;
    private Long fees;
    private Long vat;
    private String paymentMode;
    private LocalDate paymentDate;
    private String addNotes;
}
