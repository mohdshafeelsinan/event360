package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.constants.CommonConstants;
import com.microservices.drivenzy.otpservice.otpservice.modal.EventForm;
import com.microservices.drivenzy.otpservice.otpservice.modal.EventInvitation;
import com.microservices.drivenzy.otpservice.otpservice.repository.EventInvitationRepository;
import com.microservices.drivenzy.otpservice.otpservice.repository.EventRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class EventInvitationScheduler {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EventInvitationScheduler.class);

    @Autowired
    EventInvitationRepository eventInvitationRepository;

    @Autowired
    EventInvitationService eventInvitationService;

    @Autowired
    EventFormService eventFormService;

    @Autowired
    FoodOrderService foodOrderService;

    @Autowired
    MailService mailService;

    private ExecutorService executor = Executors.newFixedThreadPool(10);

//    @Scheduled(initialDelay = 0, fixedRate = 300000)
    public void fetchEventInvitationEmployeeAndUpdate() {
        try{
            logger.info("Fetching Event Invitation Employee and updating the status");
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

//    @Scheduled(initialDelay = 0, fixedRate = 300000)
    public void fetchEventAttendeesAndAttendanceListAndSendMail(){
        try{
            logger.info("Fetching Event Attendees and Attendance List and Sending Mail");
            List<EventForm> eventForms = eventFormService.getEventsByStatus("COMPLETED");
            logger.info("EventForms :: {}", eventForms);
            if(!FormatUtils.isNullOrEmpty(eventForms))
            {
                for(EventForm eventForm : eventForms)
                {
                    executor.execute(() -> {
                        try {
                            eventFormService.sendMailToEventAttendees(eventForm);
                        } catch (Exception e) {
                            logger.error("Error in fetching Event Attendees and Attendance List and Sending Mail :: Error {}", e.getMessage());
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        }catch (Exception e){
            logger.error("Error in fetching Event Attendees and Attendance List and Sending Mail :: Error {}", e.getMessage());
        }
    }

//    @Scheduled(initialDelay = 0, fixedRate = 300000)
    public void fetchEventHappeningTodayAndUpdateEmployeePresentStatus() {
        try{
            logger.info("Fetching Event Happening Today and updating the status");
            List<EventForm> eventForms = eventFormService.getEventsTodayNewQuery();
            if(!FormatUtils.isNullOrEmpty(eventForms))
            {
                for(EventForm eventForm : eventForms)
                {
                    executor.execute(() -> {
                        try {
                            eventFormService.updateEmployeePresentStatus(eventForm);
                        } catch (Exception e) {
                            logger.error("Error in updating the status of Event Happening Today and updating the status :: Error {}", e.getMessage());
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        }catch (Exception e){
            logger.error("Error in fetching Event event Happening Today and updating the status :: Error {}", e.getMessage());
        }



    }

//    @Scheduled(initialDelay = 0, fixedRate = 300000)
    public void fetchFoodOrderDetailAndSendMailtoVendor()
    {
        try{
            logger.info("Fetching Food Order Detail and Sending Mail to Vendor");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = LocalDate.now().format(formatter);
            Map<String, Double> vendorOrderDetails = foodOrderService.getVendorOrderDetails(formattedDate);
            if(!FormatUtils.isNullOrEmpty(vendorOrderDetails))
            {
                StringBuilder mailBody = new StringBuilder("Food Order Details for the day are as follows :: \n");
                for(Map.Entry<String, Double> entry : vendorOrderDetails.entrySet())
                {
                    mailBody.append(entry.getKey()).append(" :: ").append(entry.getValue()).append("\n");
                }
                mailService.sendEmail("mohammed.sinan@bajajfinserv.in", "Food Order Details for Date "+formattedDate, mailBody.toString());
            }

        }catch (Exception e){
            logger.error("Error in fetching Food Order Detail and Sending Mail to Vendor :: Error {}", e.getMessage());
        }
    }


}
