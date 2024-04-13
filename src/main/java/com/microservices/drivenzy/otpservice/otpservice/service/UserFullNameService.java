package com.microservices.drivenzy.otpservice.otpservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.drivenzy.otpservice.otpservice.dto.UserFullNameRequest;
import com.microservices.drivenzy.otpservice.otpservice.dto.UserFullNameResponse;
import com.microservices.drivenzy.otpservice.otpservice.modal.DvzUser;
import com.microservices.drivenzy.otpservice.otpservice.repository.UserRepository;

@Service
public class UserFullNameService {

	@Autowired
	private UserRepository repository;
	
	
	public UserFullNameResponse getUserFullNameResponse(UserFullNameRequest request)
	{
		UserFullNameResponse response = new UserFullNameResponse();
		try {
			List<DvzUser> userList = repository.findByMobileno(request.getMobileno());
			if(!FormatUtils.isNullOrEmpty(userList))
			{
				response.setMessage("Could Not Find Data From MONGO");
				response.setStatus("FAILURE");
			}
			else
			{
				response.setMessage("Could Not Find Data From MONGO");
				response.setStatus("FAILURE");
			}
			
		} catch (Exception e) {
			response.setMessage("Error While Finding User");
			response.setStatus("FAILURE");
		}
		
		return response;
		
	}
}
