package com.microservices.drivenzy.otpservice.otpservice.dto;

import com.microservices.drivenzy.otpservice.otpservice.modal.EventForm;
import lombok.Data;

@Data
public class EventResponse {
    private String eventId;
    private String status;
    private String messege;
    private EventForm eventForm;
}
