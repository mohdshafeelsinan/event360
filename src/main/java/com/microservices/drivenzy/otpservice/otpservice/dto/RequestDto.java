package com.microservices.drivenzy.otpservice.otpservice.dto;

import lombok.Data;

@Data
public class RequestDto {
	
	private String mobileno;
	private String email;
	private String username;
	private String password;
	private String role;
	

}
