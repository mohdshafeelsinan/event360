package com.microservices.drivenzy.otpservice.otpservice.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EventInvitationScheduler {

    @Scheduled(initialDelay = 0, fixedRate = 300000)
    public void fetchEventInvitationEmployeeAndUpdate() {

    }
}
