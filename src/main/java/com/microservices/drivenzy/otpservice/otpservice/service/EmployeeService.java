package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.controller.LeaveTrackerController;
import com.microservices.drivenzy.otpservice.otpservice.modal.EmployeeExperience;
import com.microservices.drivenzy.otpservice.otpservice.modal.Employees;
import com.microservices.drivenzy.otpservice.otpservice.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LeaveTrackerController.class);

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
            List<Employees> tempEmp = employeeRepository.findByEmail(employee.getEmail());
            if(!FormatUtils.isNullOrEmpty(tempEmp)){
                employee.setId(tempEmp.get(0).getId());
            }else{
                employee.setEmpid("EMP-" + sequenceGeneratorService.getNextSequence(Employees.SEQUENCE_NAME));
            }
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
    
    public Long getEmpCount(){
        try {
            return employeeRepository.count();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Employees> getEmployeesOfDob(){
        List<Employees> response = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            List<Employees> employeesList = getAllEmployees();
            //logger.info("Employee List :: {}",employeesList);
            for (Employees emp : employeesList){
                LocalDate birthDate = LocalDate.parse(emp.getDob(), formatter);

                LocalDate today = LocalDate.now();
                //logger.info("birthDate :: {} today :: {} Condition ::{}/{} :: {}/{}",birthDate,today,birthDate.getDayOfMonth(),today.getDayOfMonth(),birthDate.getMonth(),today.getMonth());

                if (birthDate.getDayOfMonth() == today.getDayOfMonth() && birthDate.getMonth() == today.getMonth())
                    response.add(emp);
            }
            return response;
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Inside catch");
            return null;
        }
    }
}

