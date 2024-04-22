package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.dto.CasesDto;
import com.microservices.drivenzy.otpservice.otpservice.modal.Complaints;
import com.microservices.drivenzy.otpservice.otpservice.repository.ComplaintsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ComplaintsService {

    @Autowired
    private ComplaintsRepository complaintsRepository;

    public Complaints saveComplaints(Complaints complaints){
        try {
            return complaintsRepository.save(complaints);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Complaints> getAllComplaints(){
        try {
            return complaintsRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String createCase(CasesDto casesDto) {
        try{
            String url = "https://bhfl--qav4.sandbox.my.salesforce.com/services/apexrest/createcase/";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer 00D9D0000008bAm!AQEAQMOSWuiLWhpx0aScZUCOUlKdstnTJLDjuhEguDA4dBc3Q2JVrEUivmxY6EHoUm2XMQEw0FhR5YLbvFuVgtVZ.z6HJc19");
            headers.set("Content-Type", "application/json");
            headers.set("Cookie", "BrowserId=2YytcgB6Ee-Q8pnYepxz0w; CookieConsentPolicy=0:1; LSKey-c$CookieConsentPolicy=0:1");

            String body = "{"
                    + "\"subject\": \""+casesDto.getSubject()+"\","
                    + "\"description\": \""+casesDto.getDescription()+"\","
                    + "\"status\": \""+casesDto.getStatus()+"\","
                    + "\"origin\": \""+casesDto.getOrigin()+"\","
                    + "\"priority\": \""+casesDto.getPriority()+"\""
                    + "}";

            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            System.out.println(response.getBody());

            return "Case subject : "+casesDto.getSubject();
        }catch (Exception e){
            e.printStackTrace();
            return "Case not registered";
        }

    }
}
