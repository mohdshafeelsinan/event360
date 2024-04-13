package com.microservices.drivenzy.otpservice.otpservice.service;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class SmsService {
	public static final String ACCOUNT_SID = "ACee15cf645e107b84f02d7739e19ea345"; 
    public static final String AUTH_TOKEN = "a5143d753415561b1b909e9b2bcf028c"; 
 
    public  String sendSms(String mobileno,String otp) { 
    	try {
    		Twilio.init(ACCOUNT_SID, AUTH_TOKEN); 
            Message message = Message.creator( 
                    new com.twilio.type.PhoneNumber("+91"+mobileno),  
                    "MG6bac5b50cc1796c56b8ebec1d6bcec97", 
                    "Drivezy OTP :"+otp+" Please Dont Share")      
                .create(); 
            System.out.println(message.getSid()); 
            return message.getSid();
            
		} catch (Exception e) {
			return null;
		}
        
    } 

}
