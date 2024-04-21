package com.microservices.drivenzy.otpservice.otpservice.dto;

import com.microservices.drivenzy.otpservice.otpservice.modal.Employees;
import lombok.Data;

import java.util.List;

@Data
public class Dashboard {
    private Long empCount;
    private Long eventCount;
    private List<Employees> employeesDob;
    private List<DepartmentLeaveDto> departmentLeaveDtosList;
}
