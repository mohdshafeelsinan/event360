package com.microservices.drivenzy.otpservice.otpservice.modal;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("CourseManage")
public class CourseManage {

    private List<String> courses;
    private String trainer;
    private List<String> dates;
    private Long fee;
    private List<String> attendanceDates;
}
