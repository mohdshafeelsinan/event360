package com.microservices.drivenzy.otpservice.otpservice.repository;

import com.microservices.drivenzy.otpservice.otpservice.modal.Trainer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends MongoRepository<Trainer,String>  {
    Trainer findByTrainerId(String trainerId);

}
