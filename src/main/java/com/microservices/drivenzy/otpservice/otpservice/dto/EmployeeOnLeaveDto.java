package com.microservices.drivenzy.otpservice.otpservice.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmployeeOnLeaveDto {
    private List<EmpDto> LeaveEmployees = new ArrayList<>();
    private List<EmpDto> WfhEmployees = new ArrayList<>();
    private List<EmpDto> OnSiteEmployees = new ArrayList<>();

}
