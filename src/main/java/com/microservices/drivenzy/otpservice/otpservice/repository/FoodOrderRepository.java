package com.microservices.drivenzy.otpservice.otpservice.repository;

import com.microservices.drivenzy.otpservice.otpservice.modal.FoodOrderDetails;
import com.microservices.drivenzy.otpservice.otpservice.modal.LeaveTracker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodOrderRepository extends MongoRepository<FoodOrderDetails,String> {
    List<FoodOrderDetails> findByDate(String date);
}
