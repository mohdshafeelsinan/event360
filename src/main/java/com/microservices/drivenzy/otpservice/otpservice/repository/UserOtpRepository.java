package com.microservices.drivenzy.otpservice.otpservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.microservices.drivenzy.otpservice.otpservice.modal.DvzUserOtp;

@Repository
public interface UserOtpRepository extends MongoRepository<DvzUserOtp, String>{

	List<DvzUserOtp> findByMobilenoAndOtp(String mobileno,String otp);
}
