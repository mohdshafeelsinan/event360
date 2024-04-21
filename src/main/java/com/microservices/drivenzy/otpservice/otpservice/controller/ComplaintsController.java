package com.microservices.drivenzy.otpservice.otpservice.controller;

import com.microservices.drivenzy.otpservice.otpservice.modal.CommonResponse;
import com.microservices.drivenzy.otpservice.otpservice.modal.Complaints;
import com.microservices.drivenzy.otpservice.otpservice.service.ComplaintsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/complaints")
public class ComplaintsController {
    @Autowired
    private ComplaintsService complaintsService;

    @GetMapping("/complaintsRaised")
    public CommonResponse getAllComplaintsRaised(){
        try {
            return new CommonResponse("Complaints fetched succesfully","Success",complaintsService.getAllComplaints());
        }catch (Exception e){
            return new CommonResponse("Failed to fetch Complaints","Failed",null);
        }
    }

    @PostMapping("raiseComplain")
    public CommonResponse saveComplain(@RequestBody Complaints complaints){
        try {
            return new CommonResponse("Complaint filed successfully","Success",complaintsService.saveComplaints(complaints));
        }catch (Exception e){
            return new CommonResponse("Failed to register your complaint","Failed",null);
        }
    }
}
