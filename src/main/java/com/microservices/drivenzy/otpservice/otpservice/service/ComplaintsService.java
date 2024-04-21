package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.modal.Complaints;
import com.microservices.drivenzy.otpservice.otpservice.repository.ComplaintsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintsService {

    @Autowired
    private ComplaintsRepository complaintsRepository;

    public Complaints saveComplaints(Complaints complaints){
        try {
            return complaintsRepository.save(complaints);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Complaints> getAllComplaints(){
        try {
            return complaintsRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
