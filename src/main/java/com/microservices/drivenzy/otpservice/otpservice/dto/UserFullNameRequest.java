package com.microservices.drivenzy.otpservice.otpservice.dto;

public class UserFullNameRequest {
	
	private String fullname;
	private String mobileno;

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	

}
