package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.modal.Attendance;
import com.microservices.drivenzy.otpservice.otpservice.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public Attendance saveAttendance(Attendance attendance){
        try {
            return attendanceRepository.save(attendance);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Attendance> getAttendance(){
        try {
            return attendanceRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Attendance> getAttendanceById(String studentId){
        try {
            return attendanceRepository.findByStudentId(studentId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
