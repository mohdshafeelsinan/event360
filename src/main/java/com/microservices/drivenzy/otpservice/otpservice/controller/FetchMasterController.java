package com.microservices.drivenzy.otpservice.otpservice.controller;

import com.microservices.drivenzy.otpservice.otpservice.modal.CheckListMaster;
import com.microservices.drivenzy.otpservice.otpservice.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class FetchMasterController {

    @Autowired
    private MasterService masterService;

    @GetMapping("/fetchMaster")
    public ResponseEntity<CheckListMaster> fetchMasterById(@RequestParam String id) {
        CheckListMaster checkListMaster;
        try {
            checkListMaster = masterService.fetchMasterById(id);
            checkListMaster.setStatus("Success");
            checkListMaster.setMessage("Master fetched successfully");
        } catch (Exception e) {
            e.printStackTrace();
            checkListMaster = new CheckListMaster();
            checkListMaster.setStatus("Failed");
            checkListMaster.setMessage("Failed to fetch master");
        }
        return new ResponseEntity<CheckListMaster>(checkListMaster, HttpStatus.OK);
    }
}
