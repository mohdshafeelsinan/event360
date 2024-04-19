package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.modal.CheckListMaster;
import com.microservices.drivenzy.otpservice.otpservice.repository.MasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MasterService {

    @Autowired
    MasterRepository masterRepository;

    public CheckListMaster fetchMasterById(String id) {
        Optional<CheckListMaster> checkListMaster ;
        try {
            checkListMaster = masterRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return checkListMaster.orElse(null);
    }
}
