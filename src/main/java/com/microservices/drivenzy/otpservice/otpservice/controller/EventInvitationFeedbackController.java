package com.microservices.drivenzy.otpservice.otpservice.controller;

import com.microservices.drivenzy.otpservice.otpservice.dto.EventResponse;
import com.microservices.drivenzy.otpservice.otpservice.modal.EventInvitation;
import com.microservices.drivenzy.otpservice.otpservice.service.EventInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class EventInvitationFeedbackController {

    @Autowired
    EventInvitationService eventInvitationService;

    @PostMapping("/event/invitation")
    public ResponseEntity<EventResponse> sendEventInvitation(@RequestBody EventInvitation eventInvitation) {
        EventResponse response = new EventResponse();
        try {
            eventInvitationService.saveEventInvitation(eventInvitation);
            response.setMessege("Event "+eventInvitation.getCategory()+" Sent Successfully");
            response.setStatus("SUCCESS");
            return new ResponseEntity<EventResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessege("Error In Request");
            response.setStatus("FAILURE");
            return new ResponseEntity<EventResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
