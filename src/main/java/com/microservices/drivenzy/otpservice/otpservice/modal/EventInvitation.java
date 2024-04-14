package com.microservices.drivenzy.otpservice.otpservice.modal;

import com.microservices.drivenzy.otpservice.otpservice.dto.EmpDto;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("eventdetails")
public class EventInvitation {

    @Transient
    public static final String SEQUENCE_NAME = "eventdetails_sequence";

    @Id
    private String id;
    private String eventId;
    private String eventName;
    private EmpDto employee;
    private String status;// can be pending, apparoved, rejected
    private String category;// can be invitation or feedback
    private String type;// can be event with feedback, event without feedback
    private String feedback;
    private Boolean isAttending;
    private Boolean isPresent;
    private String rating;
}
