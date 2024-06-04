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

    @Autowired
    private MailService mailService;

    public Student saveStudents(Student student){
        try {
            Student saved = studentRepository.save(student);
            String subject = "Successfully registered in Plan B Studio Fitness";
            String body = "Hi "+student.getFirstName()+". Thanks for choosing Plan B fitness." + "\n" + "Your student ID : "+student.getStudentId()+".Thank you for choosing us!\n" +
                    "\n" +
                    "Plan B Squad welcomes you for a fitter, fun-filled environment. \n" +
                    "We are excited to have you join our community, where we offer a one-stop destination for Dance, Fitness & Beyond.\n" + "\n" + "" +
                    "Student Course: "+student.getCouresEnrolled()+ "\n" + "Trainer Name: "+student.getTrainer()+ "\n" +
                    "Feel free to reach out to us at any time if you have questions or need assistance." +
                    "\n" +
                    "Plan B Squad Energy Center\n" +
                    "M 02, Mezzanine Floor, City One Building, Besides KM Trading, Al Khalidiya, Abu Dhabi\n" +
                    "+971 56 787 1125\n" +
                    "\n" +
                    "For more information, visit: https://linktr.ee/planbsquad\n";
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

    public Student getStudentById(String studentId){
        try {
            return studentRepository.findByStudentId(studentId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Student deleteStudent(String studentId){
        try {
            Student studentToDelete = getStudentById(studentId);
            if (studentToDelete!= null) {
                studentRepository.delete(studentToDelete);
                return studentToDelete; // Indicate successful deletion
            } else {
                return null; // Indicate that the student was not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Indicate an error occurred
        }
    }
}
