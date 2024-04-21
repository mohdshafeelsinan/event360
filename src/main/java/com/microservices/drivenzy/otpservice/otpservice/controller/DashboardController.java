package com.microservices.drivenzy.otpservice.otpservice.controller;

import com.microservices.drivenzy.otpservice.otpservice.modal.CommonResponse;
import com.microservices.drivenzy.otpservice.otpservice.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/getDashboardDetails")
public class DashboardController {
    @Autowired
    DashboardService dashboardService;

    @GetMapping
    public CommonResponse getDashboardDetails(){
        try {
            return new CommonResponse("Dashboard details fetched successfully.","Success",dashboardService.getDashboardDetails());
        }catch (Exception e){
            return new CommonResponse("Failed to fetch Dashboard details","Failed",null);
        }
    }
}
