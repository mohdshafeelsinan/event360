package com.microservices.drivenzy.otpservice.otpservice.service;

import java.security.SecureRandom;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.drivenzy.otpservice.otpservice.dto.RequestDto;
import com.microservices.drivenzy.otpservice.otpservice.dto.ResponseDto;
import com.microservices.drivenzy.otpservice.otpservice.dto.VerifyRequest;
import com.microservices.drivenzy.otpservice.otpservice.dto.VerifyResponse;
import com.microservices.drivenzy.otpservice.otpservice.modal.DvzUser;
import com.microservices.drivenzy.otpservice.otpservice.modal.DvzUserOtp;
import com.microservices.drivenzy.otpservice.otpservice.repository.UserOtpRepository;
import com.microservices.drivenzy.otpservice.otpservice.repository.UserRepository;

@Service
public class OtpService {
	
	@Autowired
	private UserOtpRepository otpRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private SequenceGeneratorService seqService;
	
	@Autowired SmsService smsService;
	
	public VerifyResponse getVerifyResponse(VerifyRequest request)
	{
		VerifyResponse response = new VerifyResponse();
		try {
			List<DvzUserOtp> otpUserList = otpRepository.findByMobilenoAndOtp(request.getMobileno(), request.getOtp());
			if(!FormatUtils.isNullOrEmpty(otpUserList))
			{
				DvzUserOtp otp = otpUserList.get(0);
				otpRepository.delete(otp);
				response.setMessege("OTP VERIFIED");
				response.setStatus("SUCCESS");
				response.setMobileno(request.getMobileno());
			}
			else
			{
				response.setMessege("INVALID OTP");
				response.setStatus("FAILURE");
				response.setMobileno(request.getMobileno());
			}
		} catch (Exception e) {
			response.setMessege("Could not find OTP");
			response.setStatus("FAILURE");
			response.setMobileno(request.getMobileno());
		}
		return response;
	}
	
	public ResponseDto getOtpResponse(RequestDto request)
	{
		ResponseDto response = new ResponseDto();
		SecureRandom random = new SecureRandom();
	    int num = random.nextInt(1000000);
	    String otp = String.format("%06d", num);//otp generated
		DvzUserOtp userotp = new DvzUserOtp();
		try {
			userotp.setId(seqService.getNextSequence(DvzUserOtp.SEQUENCE_NAME).toString());
			userotp.setMobileno(request.getMobileno());
			userotp.setOtp(otp);
			otpRepository.save(userotp);
			
			DvzUser user = new DvzUser();
			user.setId(seqService.getNextSequence(DvzUser.SEQUENCE_NAME).toString());
	    	user.setMobileno(request.getMobileno());
	    	userRepository.save(user);
	    	
	    	try {
	    		//request.getMobileno()
				String ssId = smsService.sendSms("7907205726", otp);
			} catch (Exception e) {
				System.err.println("sms error");
			}
			
			response.setMobileno(request.getMobileno());
			response.setOtp(otp);
			response.setStatus("SUCCESS");
		} catch (Exception e) {
			response.setMobileno(request.getMobileno());
			response.setStatus("FAILURE");
			System.out.println(e.getMessage());
		}
		
		return response;
	}
}
