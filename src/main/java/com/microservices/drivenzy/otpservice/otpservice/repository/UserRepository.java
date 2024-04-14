package com.microservices.drivenzy.otpservice.otpservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.microservices.drivenzy.otpservice.otpservice.modal.DvzUser;

@Repository
public interface UserRepository extends MongoRepository<DvzUser, String>{
	
	List<DvzUser> findByMobileno(String mobileno);

	List<DvzUser> findByEmailOrUsername(String email, String username);

	List<DvzUser> findByEmailAndPassword(String mobileno, String password);
}
