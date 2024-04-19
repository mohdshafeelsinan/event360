package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.constants.CommonConstants;
import com.microservices.drivenzy.otpservice.otpservice.dto.AttendeesDto;
import com.microservices.drivenzy.otpservice.otpservice.modal.EventForm;
import com.microservices.drivenzy.otpservice.otpservice.modal.EventInvitation;
import com.microservices.drivenzy.otpservice.otpservice.repository.EventInvitationRepository;
import com.microservices.drivenzy.otpservice.otpservice.repository.EventRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventInvitationService {

    @Autowired
    EventInvitationRepository eventInvitationRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EventInvitationScheduler.class);

    public void saveEventInvitation(EventInvitation eventInvitation) {
        try{
            eventInvitation.setId(sequenceGeneratorService.getNextSequence(EventInvitation.SEQUENCE_NAME).toString());
            eventInvitation.setStatus(CommonConstants.STATUS_PENDING);
            eventInvitationRepository.save(eventInvitation);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public void updateEventInvitationStatus(EventInvitation eventInvitation) throws Exception {
        try {
            EventForm eventForm = eventRepository.findByEventId(eventInvitation.getEventId());
            if (eventForm == null) {
                throw new Exception("Event Form not found");
            }
            AttendeesDto attendeesDto = createAttendeesDto(eventInvitation);
            switch (eventInvitation.getCategory()) {
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
            eventRepository.save(eventForm);
            eventInvitation.setStatus(CommonConstants.STATUS_APPROVED);
            eventInvitationRepository.save(eventInvitation);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in updating the status of Event Invitation Employee :: Error {}", e.getMessage());
            throw e;
        }
    }

    private AttendeesDto createAttendeesDto(EventInvitation eventInvitation) {
        AttendeesDto attendeesDto = new AttendeesDto();
        attendeesDto.setEmployee(eventInvitation.getEmployee());
        attendeesDto.setCategory(eventInvitation.getCategory());
        attendeesDto.setType(eventInvitation.getType());
        attendeesDto.setFeedback(eventInvitation.getFeedback());
        attendeesDto.setIsAttending(eventInvitation.getIsAttending());
        attendeesDto.setIsPresent(eventInvitation.getIsPresent());
        attendeesDto.setRating(eventInvitation.getRating());
        return attendeesDto;
    }
}
