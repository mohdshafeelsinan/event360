package com.microservices.drivenzy.otpservice.otpservice.dto;

import lombok.Data;

@Data
public class AttendeesDto {
    private EmpDto employee;
    private String status;
    private String category;// can be invitation or feedback
    private String type;// can be event with feedback, event without feedback
    private String feedback;
    private Boolean isAttending;
    private Boolean isPresent;
    private String rating;

}
