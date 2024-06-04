package com.microservices.drivenzy.otpservice.otpservice.modal;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("StudentPayment")
public class StudentPayment {
    private String paymentMode; //
    private String paymentDate; //
    private String course; //Single Select
    private String doj;
    private String validityDate;
    private String remarks;
    private String studentId;
    private String studentName;
    private Long fee;
    private Long vat;
}
