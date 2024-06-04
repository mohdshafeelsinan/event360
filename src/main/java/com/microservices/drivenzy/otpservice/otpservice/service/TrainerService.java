package com.microservices.drivenzy.otpservice.otpservice.service;
import com.microservices.drivenzy.otpservice.otpservice.modal.Student;
import com.microservices.drivenzy.otpservice.otpservice.modal.Trainer;
import com.microservices.drivenzy.otpservice.otpservice.repository.StudentRepository;
import com.microservices.drivenzy.otpservice.otpservice.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private MailService mailService;

    public Trainer saveTrainers(Trainer trainer){
        try {
            Trainer saved = trainerRepository.save(trainer);
            String subject = "Successfully registered in Plan B Studio Fitness";
            String body = "Hi "+trainer.getFirstName()+". Thanks for choosing Plan B fitness." + "\n" + "Your student ID : "+trainer.getTrainerId()+".Thank you for choosing us!\n" +
                    "\n" +
                    "Plan B Squad welcomes you for a fitter, fun-filled environment. \n" +
                    "We are excited to have you join our community, where we offer a one-stop destination for Dance, Fitness & Beyond.\n" + "\n" + "" +
                    "Student Course: "+trainer.getCouresEnrolled()+ "\n" + "Trainer Name: "+getTrainerById(trainer.getFirstName())+ "\n" +
                    "Feel free to reach out to us at any time if you have questions or need assistance." +
                    "\n" +
                    "Plan B Squad Energy Center\n" +
                    "M 02, Mezzanine Floor, City One Building, Besides KM Trading, Al Khalidiya, Abu Dhabi\n" +
                    "+971 56 787 1125\n" +
                    "\n" +
                    "For more information, visit: https://linktr.ee/planbsquad\n";
            mailService.sendEmail(trainer.getEmail(),subject,body);
            return saved;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Trainer> getTrainer(){
        try {
            return trainerRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Trainer getTrainerById(String trainerId){
        try {
            return trainerRepository.findByTrainerId(trainerId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
