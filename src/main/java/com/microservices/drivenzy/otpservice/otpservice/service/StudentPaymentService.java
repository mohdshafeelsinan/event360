package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.modal.Student;
import com.microservices.drivenzy.otpservice.otpservice.modal.StudentPayment;
import com.microservices.drivenzy.otpservice.otpservice.repository.StudentPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentPaymentService {
    @Autowired
    private StudentPaymentRepository studentPaymentRepository;

    public StudentPayment createStudentPayment(StudentPayment studentPayment){
        try {
            StudentPayment saved = studentPaymentRepository.save(studentPayment);
            return saved;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<StudentPayment> getStudentPayment(){
        try {
            return studentPaymentRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public StudentPayment getPaymentById(String id){
        try {
            return studentPaymentRepository.findByStudentId(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
