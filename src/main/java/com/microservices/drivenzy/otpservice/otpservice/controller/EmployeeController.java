package com.microservices.drivenzy.otpservice.otpservice.controller;
import com.microservices.drivenzy.otpservice.otpservice.dto.EmployeeResponse;
import com.microservices.drivenzy.otpservice.otpservice.modal.EmployeeExperience;
import com.microservices.drivenzy.otpservice.otpservice.modal.Employees;
import com.microservices.drivenzy.otpservice.otpservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> saveEmployee(@RequestBody Employees employee) {
        EmployeeResponse response = new EmployeeResponse();
        try {
            response.setEmployee(employeeService.saveEmployee(employee));
            response.setStatus("SUCCESS");
            response.setMessage("Employee saved successfully");
        } catch (Exception e) {
            // Handle the error here
            e.printStackTrace();
            // You can also throw a custom exception or return an error response
            response.setStatus("FAILURE");
            response.setMessage("Could not save employee");

        }
        return new ResponseEntity<EmployeeResponse>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Employees>> getAllEmployees() {
        try {
            List<Employees> employees = employeeService.getAllEmployees();
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            // Handle the error here
            e.printStackTrace();
            // You can also throw a custom exception or return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
  
    @PostMapping("/getSpecialist")
    public ResponseEntity<List<Employees>> getSpecialist(@RequestBody Employees employees){
        EmployeeExperience employeeExperience = employees.getEmployeeExperience().get(0);
        List<Employees> employeesList = employeeService.getSpecialistEmp(employeeExperience.getDomain(),employeeExperience.getYearsOfExp());
        try {
            if (employeesList.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }else {
                return ResponseEntity.ok(employeesList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Employees> getEmployeeById(@PathVariable String id) {
        try {
            Employees employee = employeeService.getEmployeeByEmpId(id);
            if (employee != null) {
                return ResponseEntity.ok(employee);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle the error here
            e.printStackTrace();
            // You can also throw a custom exception or return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
