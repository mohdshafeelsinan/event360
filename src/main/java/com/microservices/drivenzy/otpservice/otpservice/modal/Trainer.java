package com.microservices.drivenzy.otpservice.otpservice.modal;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
@Data
@Document("Trainer")
public class Trainer {
    private String firstName;
    private String lastName;
    private Long mobile;
    private Long whatsappNo;
    private String gender;
    private String email;
    private String couresEnrolled;
    private Long conductedCourse;
    private LocalDate dob;
    private String address;
    private Boolean status;
    private String trainerId;
    private String id;
    private Long trainerSalary;
}
