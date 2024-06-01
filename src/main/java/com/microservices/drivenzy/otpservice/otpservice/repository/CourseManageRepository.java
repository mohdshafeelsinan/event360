package com.microservices.drivenzy.otpservice.otpservice.repository;

import com.microservices.drivenzy.otpservice.otpservice.modal.CourseManage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseManageRepository extends MongoRepository<CourseManage,String> {
}
