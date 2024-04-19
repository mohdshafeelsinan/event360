package com.microservices.drivenzy.otpservice.otpservice.modal;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class CheckList {
    private String id;
    private String checklistId;
    private String checklistName;
    private String checklistDescription;
    private String status;

}
