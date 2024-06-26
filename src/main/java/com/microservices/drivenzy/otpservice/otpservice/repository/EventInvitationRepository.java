package com.microservices.drivenzy.otpservice.otpservice.repository;

import com.microservices.drivenzy.otpservice.otpservice.modal.EventInvitation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventInvitationRepository extends MongoRepository<EventInvitation, String> {

    List<EventInvitation> findByStatus(String statusPending);
}
