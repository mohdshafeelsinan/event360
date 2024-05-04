package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.modal.Student;
import com.microservices.drivenzy.otpservice.otpservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    private MailService mailService;

    public Student saveStudents(Student student){
        try {
            Student saved = studentRepository.save(student);
            String subject = "Successfully registered in Plan B Studio Fitness";
            String body = "Hi "+student.getFirstName()+". Thanks for choosing Plan B fitness. Your student ID : "+student.getStudentId();
            mailService.sendEmail(student.getEmail(),subject,body);
            return saved;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Student> getStudent(){
        try {
            return studentRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
