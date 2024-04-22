package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.constants.CommonConstants;
import com.microservices.drivenzy.otpservice.otpservice.dto.AttendeesDto;
import com.microservices.drivenzy.otpservice.otpservice.dto.EmpDto;
import com.microservices.drivenzy.otpservice.otpservice.dto.EventRequestDto;
import com.microservices.drivenzy.otpservice.otpservice.dto.EventResponse;
import com.microservices.drivenzy.otpservice.otpservice.modal.EventForm;
import com.microservices.drivenzy.otpservice.otpservice.repository.EventRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class EventFormService {

    @Autowired
    private EventRepository eventFormRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private LeaveTrackerService leaveTrackerService;

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
            eventForm.setEventAttendanceQRCode(FormatUtils.generateQrCode("http://192.168.1.7:4200/event/eventAttendance/"+eventForm.getEventId()));
            eventForm.setRemainingBudget(calculateRemainingBudget(eventForm));
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

    public EventResponse updateEventForm(EventForm eventForm) {
        EventResponse response = new EventResponse();
        try {
            eventForm.setRemainingBudget(calculateRemainingBudget(eventForm));
            eventFormRepository.save(eventForm);
            response.setMessege("Event Updated Successfully");
            response.setStatus("SUCCESS");
            response.setEventId(eventForm.getId());
        } catch (Exception e) {
            logger.error("Error in updating the status of Event Form :: Error {}", e.getMessage());
            // Handle the exception or log the error
            e.printStackTrace();
            response.setMessege("Could not find Event");
            response.setStatus("FAILURE");
            response.setEventId(null);
        }
        return response;
    }


    public List<EventForm> getAllEvents() {
        try {
            logger.info("Getting all the events");
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

    public Double calculateRemainingBudget(EventForm event) {
        if(FormatUtils.isNullOrEmpty(event.getExpenses())) {
            return 0.0;
        }
        double totalExpenses = event.getExpenses().values().stream().mapToDouble(Double::doubleValue).sum();
        Double remainingBudget = event.getBudget() - totalExpenses;
        return remainingBudget;
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
                    sendMail(eventRequestDto.getEmployeeMail(), "Volunteer Confirmation", "You have been confirmed as a volunteer for the event "+eventForm.getEventName());
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

    private void sendMail(String email, String subject, String message) {
        try{
            mailService.sendEmail(email, subject, message);
        }catch (Exception e){
            logger.error("Error in sending mail :: Error {}", e.getMessage());
        }
    }

    public  List<EventForm> getEventsByStatus(String status) {
        try {
            return eventFormRepository.findByStatus(status);
        } catch (Exception e) {
            // Handle the exception or log the error
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of an error
        }
    }

    public List<EventForm> getEventsTodayNewQuery() {
        String fromDate = LocalDate.now().toString();
        String toDate = LocalDate.now().plusDays(1).toString();
        logger.info("From Date :: {} To Date :: {}",fromDate, toDate);

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        LocalDateTime fromDate = LocalDateTime.now().withHour(0).withMinute(1).withSecond(40).withNano(135000000);
//        LocalDateTime toDate = LocalDateTime.now().plusDays(1).withHour(23).withMinute(55).withSecond(40).withNano(135000000);
//        String fromDateStr = fromDate.format(formatter);
//        String toDateStr = toDate.format(formatter);
//        logger.info("From Date :: {} To Date :: {}", fromDateStr, toDateStr);

        return eventFormRepository.findByEventFromDateBetween(fromDate, toDate);
    }

    public void updateEmployeePresentStatus(EventForm eventForm) {
        try {
            logger.info("Updating the status of Employee Present Status :: {}",eventForm.toString());
            LocalDate today = LocalDate.now();
            String day = String.valueOf(today.getDayOfMonth());
            String month = today.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            String year = String.valueOf(today.getYear());
            logger.info("Day :: {} Month :: {} Year :: {}",day, month, year);

            leaveTrackerService.findEmployeesOnLeave1(month,day,year).forEach(emp -> {
                eventForm.getAttendance().forEach(attendeesDto -> {
                    if(attendeesDto.getEmployee().getEmail().equals(emp) || attendeesDto.getEmployee().getEmail().equals(emp+"@bajajfinserv.in")){
                        attendeesDto.setIsPresent(false);
                    }
                    else {
                        attendeesDto.setIsPresent(true);
                    }
                });
            });
            eventFormRepository.save(eventForm);
        } catch (Exception e) {
            logger.error("Error in updating the status of Employee Present Status :: Error {}", e.getMessage());
            // Handle the exception or log the error
            e.printStackTrace();
        }
    }

    public Long getEventCount(){
        try {
            return eventFormRepository.count();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void sendMailToEventAttendees(EventForm eventForm) {
        List<AttendeesDto> attendees = eventForm.getAttendees();
        List<AttendeesDto> attendance = eventForm.getAttendance();

        // compare the attendees and attendance list the employee not in attendance list send mail
        attendees.forEach(attendeesDto -> {
            if(attendance.stream().noneMatch(attendeesDto1 -> attendeesDto1.getEmployee().getEmail().equals(attendeesDto.getEmployee().getEmail()))){
                logger.info("Sending mail to employee :: {}",attendeesDto.getEmployee().getEmail());
                sendMail(attendeesDto.getEmployee().getEmail(), "Event Feedback", "Please Complete your feedback .You have not marked your attendance for the event "+eventForm.getEventName());
            }
        });

    }
}
