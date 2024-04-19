package com.microservices.drivenzy.otpservice.otpservice.modal;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("master")
public class CheckListMaster {

    @Id
    private String id;
    private String mastername;
    private List<CheckList> checklist = new ArrayList<>();
    private String status;
    private String message;
}
