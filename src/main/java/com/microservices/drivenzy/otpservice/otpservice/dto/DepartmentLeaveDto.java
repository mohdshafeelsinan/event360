package com.microservices.drivenzy.otpservice.otpservice.dto;

import lombok.Data;

@Data
public class DepartmentLeaveDto {
    private String department;
    private int leaveCount;
    private int wfhCount;
    private int onSiteCount;
}
