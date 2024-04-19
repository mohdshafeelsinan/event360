package com.microservices.drivenzy.otpservice.otpservice.modal;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document("leavetracker")
public class LeaveTracker {

    public static final String SEQUENCE_NAME = "leave_sequence";

    @Id
    private String id;

    private String month;
    private String year;
    private Map<String, Map<String, Integer>> employeeAttendance;
}
