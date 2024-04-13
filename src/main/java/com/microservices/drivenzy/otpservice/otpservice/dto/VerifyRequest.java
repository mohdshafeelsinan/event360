package com.microservices.drivenzy.otpservice.otpservice.dto;

public class VerifyRequest {
	
	private String otp;
	private String mobileno;
	
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	

}
