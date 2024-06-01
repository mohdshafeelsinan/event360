package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.modal.Attendance;
import com.microservices.drivenzy.otpservice.otpservice.modal.CourseManage;
import com.microservices.drivenzy.otpservice.otpservice.repository.CourseManageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseManageService {

    @Autowired
    private CourseManageRepository courseManageRepository;

    public CourseManage saveCourseManage(CourseManage courseManage){
        try {
            return courseManageRepository.save(courseManage);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
