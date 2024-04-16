package com.microservices.drivenzy.otpservice.otpservice.controller;

import com.microservices.drivenzy.otpservice.otpservice.modal.CommonResponse;
import com.microservices.drivenzy.otpservice.otpservice.service.LeaveTrackerService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class LeaveTrackerController {

    @Autowired
    private LeaveTrackerService leaveTrackerService;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LeaveTrackerController.class);

    @PostMapping("/upload")
    public CommonResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("month") String month, @RequestParam("year") String year) {
        try{
            leaveTrackerService.importExcelFile(file, month);
            return new CommonResponse("File uploaded successfully", "success", null);
        } catch (Exception e) {
            return new CommonResponse("File upload failed", "failed", null);
        }
    }

    @PostMapping("/employeesOnLeave")
    public CommonResponse getEmployeesOnLeave(@RequestParam("month") String month, @RequestParam("day") String day) {
        logger.info("Getting employees on leave"+month+" "+day);
        try{
            return new CommonResponse("Employees on leave", "success", leaveTrackerService.findEmployeesOnLeave1(month, day));
        } catch (Exception e) {
            return new CommonResponse("Failed to get employees on leave", "failed", null);
        }
    }
}
