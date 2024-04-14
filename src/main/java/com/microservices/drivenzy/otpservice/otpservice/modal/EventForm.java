package com.microservices.drivenzy.otpservice.otpservice.modal;

import com.microservices.drivenzy.otpservice.otpservice.dto.AttendeesDto;
import com.microservices.drivenzy.otpservice.otpservice.dto.EmpDto;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private boolean isInvitationRequired;
    private boolean isVotable;
    private boolean isSnacks;
    private boolean needVolunteer;
    private List<AttendeesDto> attendees = new ArrayList<>();// who is attendeng and not attending
    private List<AttendeesDto> attendance = new ArrayList<>();//once event complete the survey will be added here
    private List<AttendeesDto> volunteer = new ArrayList<>();//volunteer


    private String eventInvitationQRCode;
    private String eventQRCode;
    private String eventAttendanceQRCode;

    //Budget
    private Double budget;
    private Double remainingBudget;
    private Map<String, Double> expenses;


    //checklist
    private List<EventCheckList> checkList = new ArrayList<>();
}
