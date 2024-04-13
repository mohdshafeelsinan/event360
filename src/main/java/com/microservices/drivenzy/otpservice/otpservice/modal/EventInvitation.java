package com.microservices.drivenzy.otpservice.otpservice.modal;

import com.microservices.drivenzy.otpservice.otpservice.dto.EmpDto;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("eventinvitation")
public class EventInvitation {

    @Transient
    public static final String SEQUENCE_NAME = "eventinvitation_sequence";

    @Id
    private String id;
    private String eventId;
    private String eventName;
    private EmpDto employee;
    private String status;
}
