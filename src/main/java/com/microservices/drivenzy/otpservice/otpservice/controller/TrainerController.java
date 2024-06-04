package com.microservices.drivenzy.otpservice.otpservice.controller;


import com.microservices.drivenzy.otpservice.otpservice.modal.CommonResponse;
import com.microservices.drivenzy.otpservice.otpservice.modal.Student;
import com.microservices.drivenzy.otpservice.otpservice.modal.Trainer;
import com.microservices.drivenzy.otpservice.otpservice.service.LeaveTrackerService;
import com.microservices.drivenzy.otpservice.otpservice.service.StudentService;
import com.microservices.drivenzy.otpservice.otpservice.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/trainer")
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    private static final java.util.logging.Logger logger = Logger.getLogger(TrainerController.class.getName());

    @PostMapping("/saveTrainer")
    public CommonResponse saveTrainer(@RequestBody Trainer trainer){
        try {
            return new CommonResponse("Trainer details saved successfully","Success",trainerService.saveTrainers(trainer));
        }catch (Exception e){
            return new CommonResponse("Failed to save trainer details","Failed",null);
        }
    }

    @GetMapping("/getTrainer")
    public CommonResponse getAllTrainers(){
        try {
            return new CommonResponse("Trainer details fetched successfully","Success",trainerService.getTrainer());
        }catch (Exception e){
            return new CommonResponse("Failed to fetch trainer details","Failed",null);
        }
    }

    @GetMapping("/getTrainer/{id}")
    public CommonResponse getTrainerById(@PathVariable String id){
        try {
            logger.info("TrainerId "+ id);
            return new CommonResponse("Trainer details fetched successfully","Success",trainerService.getTrainerById(id));
        }catch (Exception e){
            return new CommonResponse("Failed to fetch trainer details","Failed",null);
        }
    }
}
