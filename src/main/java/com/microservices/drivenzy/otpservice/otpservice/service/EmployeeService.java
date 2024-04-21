package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.modal.EmployeeExperience;
import com.microservices.drivenzy.otpservice.otpservice.modal.Employees;
import com.microservices.drivenzy.otpservice.otpservice.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<Employees> getSpecialistEmp(String domain, Double exp){
        List<Employees> allemp = getAllEmployees();
        List<Employees> result = new ArrayList<>();
        for (Employees emp : allemp){
            List<EmployeeExperience> employeeExperiences = emp.getEmployeeExperience();
            if(employeeExperiences == null) continue;
            for(EmployeeExperience empExp : employeeExperiences){
                if(empExp.getDomain().equals(domain) && empExp.getYearsOfExp()>exp){
                    result.add(emp);
                }
            }
        }
        return result;
    }
    public Employees getEmployeeById(String id) {
        try {
            return employeeRepository.findById(id).orElse(null);
        } catch (Exception e) {
            // Handle the exception here
            e.printStackTrace();
            return null; // Or throw a custom exception
        }
    }

    public Employees getEmployeeByEmpId(String empId) {
        try {
            return employeeRepository.findByEmpid(empId);
        } catch (Exception e) {
            // Handle the exception here
            e.printStackTrace();
            return null; // Or throw a custom exception
        }
    }

    public List<Employees> getEmployeeByEmail(String email) {
        try {
            return employeeRepository.findByEmail(email);
        } catch (Exception e) {
            // Handle the exception here
            e.printStackTrace();
            return null; // Or throw a custom exception
        }
    }
}

