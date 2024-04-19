package com.microservices.drivenzy.otpservice.otpservice.dto;

import lombok.Data;

@Data
public class ResponseDto {
	
	private String mobileno;
	private String status;
	private String otp;
	private String message;
	private String email;
	private String username;
	private String role;
	
	

}
