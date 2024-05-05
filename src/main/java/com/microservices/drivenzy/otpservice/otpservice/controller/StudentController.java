package com.microservices.drivenzy.otpservice.otpservice.controller;

import com.microservices.drivenzy.otpservice.otpservice.modal.CommonResponse;
import com.microservices.drivenzy.otpservice.otpservice.modal.Student;
import com.microservices.drivenzy.otpservice.otpservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/saveStudent")
    public CommonResponse saveStudent(@RequestBody Student student){
        try {
            return new CommonResponse("Student details saved successfully","Success",studentService.saveStudents(student));
        }catch (Exception e){
            return new CommonResponse("Failed to save student details","Failed",null);
        }
    }

    @PostMapping("/getStudent")
    public CommonResponse getAllStudents(){
        try {
            return new CommonResponse("Student details fetched successfully","Success",studentService.getStudent());
        }catch (Exception e){
            return new CommonResponse("Failed to fetch student details","Failed",null);
        }
    }
}
