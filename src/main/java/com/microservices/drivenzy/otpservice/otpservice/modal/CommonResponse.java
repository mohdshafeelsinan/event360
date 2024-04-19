package com.microservices.drivenzy.otpservice.otpservice.modal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonResponse {
    private String message;
    private String status;
    private Object data;
}
