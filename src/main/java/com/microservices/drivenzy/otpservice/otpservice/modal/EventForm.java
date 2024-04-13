package com.microservices.drivenzy.otpservice.otpservice.modal;

import com.microservices.drivenzy.otpservice.otpservice.dto.EmpDto;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("eventform")
public class EventForm {
    @Transient
    public static final String SEQUENCE_NAME = "event_sequence";

    @Id
    private String id;

    private String eventId;
    private String eventName;
    private String eventDescription;
    private String eventFromDate;
    private String eventToDate;
    private String eventStartTime;
    private String eventEndTime;
    private String eventLocation;
    private String status;
    
    private EmpDto organizer;
    private String department;
    private String eventType;
    private int maxAttendees;
    private boolean requiresRSVP;
    private boolean isInternalEvent;
    private List<EmpDto> attendees = new ArrayList<>();
    private List<EmpDto> invitedEmployees = new ArrayList<>();
    private List<EmpDto> declinedEmployees = new ArrayList<>();


    private String eventInvitationQRCode;
    private String eventQRCode;
    private String eventAttendanceQRCode;
}
