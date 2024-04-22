package com.microservices.drivenzy.otpservice.otpservice.dto;

import lombok.Data;

@Data
public class CasesDto {

    private String subject;
    private String description;
    private String status;
    private String origin;
    private String priority;
}
