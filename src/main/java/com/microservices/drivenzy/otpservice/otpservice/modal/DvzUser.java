package com.microservices.drivenzy.otpservice.otpservice.modal;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("drivenzyuser")
public class DvzUser {
	
	@Transient
    public static final String SEQUENCE_NAME = "event_sequence";
	
	@Id
	private String id;
	
	private String mobileno;
	private String username;
	private String password;
	private String fullname;
	private String email;
	private String role;
	
	

}
