package com.microservices.drivenzy.otpservice.otpservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.drivenzy.otpservice.otpservice.dto.RequestDto;
import com.microservices.drivenzy.otpservice.otpservice.dto.ResponseDto;
import com.microservices.drivenzy.otpservice.otpservice.dto.VerifyRequest;
import com.microservices.drivenzy.otpservice.otpservice.dto.VerifyResponse;
import com.microservices.drivenzy.otpservice.otpservice.service.OtpService;

@RestController
@RequestMapping("/api/bfl/")
public class OtpRestController {
	
	@Autowired
	private OtpService service;
	
	@GetMapping("/testkube")
	public String getKubeMsg()
	{
		return "Hello Docker Kubectl";
	}
	
	@PostMapping("/generateotp")
	public ResponseEntity<ResponseDto> generateOTP(@RequestBody RequestDto request)
	{
		ResponseDto response = new ResponseDto();
		try {
			response = service.getOtpResponse(request);
		} catch (Exception e) {
			response.setStatus("Failed");
		}
		return new ResponseEntity<ResponseDto>(response,HttpStatus.OK);
	}
	
	@PostMapping("/verifyotp")
	public ResponseEntity<VerifyResponse> verifyOTP(@RequestBody VerifyRequest request)
	{
		VerifyResponse response = new VerifyResponse();
		try {
			response = service.getVerifyResponse(request);
		} catch (Exception e) {
			response.setMessege("Error In Request");
			response.setStatus("FAILURE");
		}
		return new ResponseEntity<VerifyResponse>(response,HttpStatus.OK);
	}
	

}
