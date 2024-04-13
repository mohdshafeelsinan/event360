package com.microservices.drivenzy.otpservice.otpservice.dto;

import lombok.Data;

@Data
public class EventResponse {
    private String eventId;
    private String status;
    private String messege;
}
