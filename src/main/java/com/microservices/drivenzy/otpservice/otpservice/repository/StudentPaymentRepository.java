package com.microservices.drivenzy.otpservice.otpservice.repository;

import com.microservices.drivenzy.otpservice.otpservice.modal.StudentPayment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentPaymentRepository extends MongoRepository<StudentPayment,String> {
    StudentPayment findByStudentId(String studentId);
}
