package com.microservices.drivenzy.otpservice.otpservice.modal;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("Attendance")
public class Attendance {
    private List<String> date;
    private String course;
    private String studentName;
    private String studentId;
}
