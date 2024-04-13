package com.microservices.drivenzy.otpservice.otpservice.repository;
import com.microservices.drivenzy.otpservice.otpservice.modal.EventForm;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<EventForm, String> {

    EventForm findByEventId(String eventId);
    EventForm findByEventName(String eventName);

    List<EventForm> findByEventFromDate(String todayDate);
}
