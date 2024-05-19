package com.microservices.drivenzy.otpservice.otpservice.controller;

import com.microservices.drivenzy.otpservice.otpservice.modal.CommonResponse;
import com.microservices.drivenzy.otpservice.otpservice.modal.Student;
import com.microservices.drivenzy.otpservice.otpservice.modal.StudentPayment;
import com.microservices.drivenzy.otpservice.otpservice.repository.StudentPaymentRepository;
import com.microservices.drivenzy.otpservice.otpservice.service.StudentPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/payment")
public class StudentPaymentController {

    @Autowired
    private StudentPaymentService studentPaymentService;

    @PostMapping("/savePayment")
    public CommonResponse savePayment(@RequestBody StudentPayment payment){
        try {
            return new CommonResponse("Payment details saved successfully","Success",studentPaymentService.createStudentPayment(payment));
        }catch (Exception e){
            return new CommonResponse("Failed to save Payment details","Failed",null);
        }
    }

    @PostMapping("/getPayment")
    public CommonResponse getAllPayments(){
        try {
            return new CommonResponse("Payment details fetched successfully","Success",studentPaymentService.getStudentPayment());
        }catch (Exception e){
            return new CommonResponse("Failed to fetch Payment details","Failed",null);
        }
    }

    @GetMapping("/getPayment/{id}")
    public CommonResponse getPaymentById(@PathVariable String id){
        try {
            return new CommonResponse("Payment details fetched successfully","Success",studentPaymentService.getPaymentById(id));
        }catch (Exception e){
            return new CommonResponse("Failed to fetch Payment details","Failed",null);
        }
    }
}
