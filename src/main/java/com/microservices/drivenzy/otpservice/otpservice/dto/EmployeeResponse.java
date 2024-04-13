
package com.microservices.drivenzy.otpservice.otpservice.dto;

import com.microservices.drivenzy.otpservice.otpservice.modal.Employees;
import lombok.Data;

@Data
public class EmployeeResponse {
    private String message;
    private String status;
    private Employees employee;
}
