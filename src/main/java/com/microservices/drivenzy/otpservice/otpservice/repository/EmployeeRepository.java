package com.microservices.drivenzy.otpservice.otpservice.repository;

import com.microservices.drivenzy.otpservice.otpservice.modal.Employees;
import com.microservices.drivenzy.otpservice.otpservice.modal.EventForm;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employees, String> {
    List<Employees> findByEmail(String email);

    Employees findByEmpId(String empId);
}
