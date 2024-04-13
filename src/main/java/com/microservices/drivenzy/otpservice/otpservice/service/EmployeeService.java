package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.modal.Employees;
import com.microservices.drivenzy.otpservice.otpservice.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employees> getAllEmployees() {
        try {
            return employeeRepository.findAll();
        } catch (Exception e) {
            // Handle the exception here
            e.printStackTrace();
            return null; // Or throw a custom exception
        }
    }

    public Employees saveEmployee(Employees employee) {
        try {
            return employeeRepository.save(employee);
        } catch (Exception e) {
            // Handle the exception here
            e.printStackTrace();
            return null; // Or throw a custom exception
        }
    }
}

