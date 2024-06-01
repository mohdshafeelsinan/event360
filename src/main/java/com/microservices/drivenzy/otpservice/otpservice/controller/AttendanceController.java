package com.microservices.drivenzy.otpservice.otpservice.controller;

import com.microservices.drivenzy.otpservice.otpservice.modal.Attendance;
import com.microservices.drivenzy.otpservice.otpservice.modal.CommonResponse;
import com.microservices.drivenzy.otpservice.otpservice.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/saveAttendance")
    public CommonResponse saveAttendance(@RequestBody Attendance attendance){
        try {
            return new CommonResponse("Attendance details saved successfully","Success",attendanceService.saveAttendance(attendance));
        }catch (Exception e){
            return new CommonResponse("Failed to save Attendance details","Failed",null);
        }
    }

    @GetMapping("/getAttendance")
    public CommonResponse getAllAttendance(){
        try {
            return new CommonResponse("Attendance details fetched successfully","Success",attendanceService.getAttendance());
        }catch (Exception e){
            return new CommonResponse("Failed to fetch Attendance details","Failed",null);
        }
    }

    @GetMapping("/getAttendance/{id}")
    public CommonResponse getAttendanceById(@PathVariable String id){
        try {
            return new CommonResponse("Attendance details fetched successfully","Success",attendanceService.getAttendanceById(id));
        }catch (Exception e){
            return new CommonResponse("Failed to fetch Attendance details","Failed",null);
        }
    }
}
