package com.microservices.drivenzy.otpservice.otpservice.repository;

import com.microservices.drivenzy.otpservice.otpservice.modal.LeaveTracker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveTrackerRepository extends MongoRepository<LeaveTracker, String> {
    List<LeaveTracker> findByMonth(String month);

    List<LeaveTracker> findByMonthAndYear(String month, String year);
}
