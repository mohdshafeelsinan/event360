package com.microservices.drivenzy.otpservice.otpservice.modal;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("complaints")
public class Complaints {
    @Transient
    public static final String SEQUENCE_NAME = "complaints_sequence";

    @Id
    private String id;
    private String complaintId;
    private String userId;
    private String userEmail;
    private String complaintTitle;
    private String complaintDescription;
    private String status;
    private Boolean isAnonymous;
    private String category;

}
