package com.microservices.drivenzy.otpservice.otpservice.dto;

public class UserFullNameResponse {
	
	private String status;
	private String message;
	private String fullanme;
	private String mobileno;
	
	public String getFullanme() {
		return fullanme;
	}
	public void setFullanme(String fullanme) {
		this.fullanme = fullanme;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
