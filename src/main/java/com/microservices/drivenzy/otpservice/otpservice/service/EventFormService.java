package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.constants.CommonConstants;
import com.microservices.drivenzy.otpservice.otpservice.dto.AttendeesDto;
import com.microservices.drivenzy.otpservice.otpservice.dto.EmpDto;
import com.microservices.drivenzy.otpservice.otpservice.dto.EventRequestDto;
import com.microservices.drivenzy.otpservice.otpservice.dto.EventResponse;
import com.microservices.drivenzy.otpservice.otpservice.modal.DvzUserOtp;
import com.microservices.drivenzy.otpservice.otpservice.modal.EventForm;
import com.microservices.drivenzy.otpservice.otpservice.repository.EventRepository;
import org.slf4j.Logger;
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

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EventFormService.class);

    @Autowired
    private SequenceGeneratorService seqService;
    public EventResponse saveEventForm(EventForm eventForm) {
        EventResponse response = new EventResponse();
        try {
            logger.info("Saving Event Form :: {}",eventForm.toString());
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

    public EventForm updateEventForm(EventRequestDto eventRequestDto) {
        try {
            logger.info("Updating the status of Event Form :: {}",eventRequestDto.toString());
            EventForm eventForm = eventFormRepository.findByEventId(eventRequestDto.getEventId());
            AttendeesDto attendeesDto = new AttendeesDto();
            EmpDto empDto = new EmpDto();
            empDto.setEmail(eventRequestDto.getEmployeeMail());
            attendeesDto.setEmployee(empDto);
            attendeesDto.setCategory(eventRequestDto.getCategory());
            attendeesDto.setType(eventRequestDto.getType());
            attendeesDto.setStatus(CommonConstants.STATUS_APPROVED);
            attendeesDto.setFeedback(eventRequestDto.getFeedback());
            attendeesDto.setRating(eventRequestDto.getRating());
            attendeesDto.setIsAttending(eventRequestDto.getIsAttending());
            attendeesDto.setIsPresent(eventRequestDto.getIsPresent());

            switch (eventRequestDto.getCategory()) {
                case CommonConstants.EVENT_CATEGORY_INVITATION:
                    eventForm.getAttendees().add(attendeesDto);
                    break;
                case CommonConstants.EVENT_CATEGORY_FEEDBACK:
                    eventForm.getAttendance().add(attendeesDto);
                    break;
                case CommonConstants.EVENT_CATEGORY_VOLUNTEER:
                    eventForm.getVolunteer().add(attendeesDto);
                    break;
                default:
                    logger.error("Invalid category");
                    throw new Exception("Invalid category");
            }
            logger.info("Updating the status of Event Form :: {}",eventForm.toString());
            return eventFormRepository.save(eventForm);
        } catch (Exception e) {
            logger.error("Error in updating the status of Event Form :: Error {}", e.getMessage());
            // Handle the exception or log the error
            e.printStackTrace();
            return null; // Return null in case of an error
        }
    }
}
