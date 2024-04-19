package com.microservices.drivenzy.otpservice.otpservice.repository;
import com.microservices.drivenzy.otpservice.otpservice.modal.EventForm;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<EventForm, String> {

    EventForm findByEventId(String eventId);
    EventForm findByEventName(String eventName);

    List<EventForm> findByEventFromDate(String todayDate);
    @Query("{ 'eventFromDate': { $gt: ?0, $lt: ?1 } }")
    List<EventForm> findByEventFromDateBetween(String startDate, String endDate);
}
