package com.microservices.drivenzy.otpservice.otpservice.modal;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("drivenzyuserotp")
public class DvzUserOtp {
	

	@Transient
    public static final String SEQUENCE_NAME = "usersotp_sequence";
	
	@Id
    private String id;
	private String otp;
	private String mobileno;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
