package com.microservices.drivenzy.otpservice.otpservice.controller;


import com.microservices.drivenzy.otpservice.otpservice.modal.Attendance;
import com.microservices.drivenzy.otpservice.otpservice.modal.CommonResponse;

import com.microservices.drivenzy.otpservice.otpservice.modal.CourseManage;
import com.microservices.drivenzy.otpservice.otpservice.service.CourseManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/courseManage")
public class CourseManageController {

    @Autowired
    private CourseManageService courseManageService;

    @PostMapping("/saveCourseManage")
    public CommonResponse saveCourseManage(@RequestBody CourseManage courseManage) {
        try {
            return new CommonResponse("Attendance details saved successfully", "Success", courseManageService.saveCourseManage(courseManage));
        } catch (Exception e) {
            return new CommonResponse("Failed to save Attendance details", "Failed", null);
        }
    }
}