package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.modal.EventInvitation;
import com.microservices.drivenzy.otpservice.otpservice.repository.EventInvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventInvitationService {

    @Autowired
    EventInvitationRepository eventInvitationRepository;

    public void saveEventInvitation(EventInvitation eventInvitation) {
        try{
            eventInvitationRepository.save(eventInvitation);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
