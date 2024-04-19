package com.microservices.drivenzy.otpservice.otpservice.controller;

import com.microservices.drivenzy.otpservice.otpservice.modal.CommonResponse;
import com.microservices.drivenzy.otpservice.otpservice.service.LeaveTrackerService;
import com.microservices.drivenzy.otpservice.otpservice.service.MailService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
public class LeaveTrackerController {

    @Autowired
    private LeaveTrackerService leaveTrackerService;

    @Autowired
    MailService mailService;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LeaveTrackerController.class);

    @PostMapping("/upload")
    public CommonResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("month") String month, @RequestParam("year") String year) {
        try{
            leaveTrackerService.importExcelFile(file, month, year);
            return new CommonResponse("File uploaded successfully", "success", null);
        } catch (Exception e) {
            return new CommonResponse("File upload failed", "failed", null);
        }
    }

    @PostMapping("/employeesOnLeave")
    public CommonResponse getEmployeesOnLeave(@RequestParam("month") String month, @RequestParam("day") String day, @RequestParam("year") String year) {
        logger.info("Getting employees on leave"+month+" "+day);
        try{
            return new CommonResponse("Employees on leave", "success", leaveTrackerService.findEmployeesOnLeaveDetails(month, day, year));
        } catch (Exception e) {
            return new CommonResponse("Failed to get employees on leave", "failed", null);
        }
    }

    @GetMapping("/sendMail")
    public void sendMail() {
        logger.info("Sending mail");
        mailService.sendEmail("jkjoseph2023@gmail.com", "Test", "Hi jasa how areu");
    }
}
