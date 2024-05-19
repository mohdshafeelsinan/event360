package com.microservices.drivenzy.otpservice.otpservice.modal;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("StudentPayment")
public class StudentPayment {
    private String paymentMode;
    private String paymentDate;
    private String category;
    private String doj;
    private String validityDate;
    private Long classCompleted;
    private Long classRemaining;
    private String studentId;
    private Long vat;
}
