package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.dto.EventResponse;
import com.microservices.drivenzy.otpservice.otpservice.modal.DvzUserOtp;
import com.microservices.drivenzy.otpservice.otpservice.modal.EventForm;
import com.microservices.drivenzy.otpservice.otpservice.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventFormService {

    @Autowired
    private EventRepository eventFormRepository;

    @Autowired
    private SequenceGeneratorService seqService;
    public EventResponse saveEventForm(EventForm eventForm) {
        EventResponse response = new EventResponse();
        try {
            eventForm.setId(seqService.getNextSequence(EventForm.SEQUENCE_NAME).toString());
            eventForm.setEventId("BFL-EVT"+eventForm.getId());
            eventForm.setEventInvitationQRCode(FormatUtils.generateQrCode("www.google.com"+"/"+eventForm.getEventName()));
            eventForm.setEventQRCode(FormatUtils.generateQrCode("www.google.com"+"/"+eventForm.getEventName()));
            eventForm.setEventAttendanceQRCode(FormatUtils.generateQrCode("www.google.com"+"/"+eventForm.getEventName()));
            eventForm = eventFormRepository.save(eventForm);
            response.setMessege("Event Saved Successfully");
            response.setStatus("SUCCESS");
            response.setEventId(eventForm.getId());
        } catch (Exception e) {
            response.setMessege("Could not find OTP");
            response.setStatus("FAILURE");
            response.setEventId(null);
        }
        return response;
        
    }


    public List<EventForm> getAllEvents() {
        try {
            return eventFormRepository.findAll();
        } catch (Exception e) {
            // Handle the exception or log the error
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of an error
        }
    }

    public EventForm getEventById(String eventId) {
        try {
            return eventFormRepository.findByEventId(eventId);
        } catch (Exception e) {
            // Handle the exception or log the error
            e.printStackTrace();
            return null; // Return null in case of an error
        }
    }

    public EventForm getEventByName(String eventName) {
        try {
            return eventFormRepository.findByEventName(eventName);
        } catch (Exception e) {
            // Handle the exception or log the error
            e.printStackTrace();
            return null; // Return null in case of an error
        }
    }   

    public List<EventForm> getEventsToday() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate today = LocalDate.now();
            String todysDate = today.format(formatter);
            return eventFormRepository.findByEventFromDate(todysDate);
        } catch (Exception e) {
            // Handle the exception or log the error
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of an error
        }
    }
}
