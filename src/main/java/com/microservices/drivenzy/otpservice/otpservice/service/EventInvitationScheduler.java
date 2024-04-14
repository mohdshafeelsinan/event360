package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.constants.CommonConstants;
import com.microservices.drivenzy.otpservice.otpservice.modal.EventInvitation;
import com.microservices.drivenzy.otpservice.otpservice.repository.EventInvitationRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class EventInvitationScheduler {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EventInvitationScheduler.class);

    @Autowired
    EventInvitationRepository eventInvitationRepository;

    @Autowired
    EventInvitationService eventInvitationService;

    private ExecutorService executor = Executors.newFixedThreadPool(10);

    @Scheduled(initialDelay = 0, fixedRate = 300000)
    public void fetchEventInvitationEmployeeAndUpdate() {
        try{
            logger.info("Fetching Event Invitation Employee and updating the status");
            //write a executerservice code with thread 10 and update the status of the event invitation
            List<EventInvitation> eventInvitations = eventInvitationRepository.findByStatus(CommonConstants.STATUS_PENDING);
            if(!FormatUtils.isNullOrEmpty(eventInvitations))
            {
                for(EventInvitation eventInvitation : eventInvitations)
                {
                    executor.execute(() -> {
                        try {
                            eventInvitationService.updateEventInvitationStatus(eventInvitation);
                        } catch (Exception e) {
                            logger.error("Error in updating the status of Event Invitation Employee :: Error {}", e.getMessage());
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        }catch (Exception e){
            logger.error("Error in fetching Event Invitation Employee and updating the status :: Error {}", e.getMessage());
        }



    }
}
