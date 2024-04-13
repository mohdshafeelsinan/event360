package com.microservices.drivenzy.otpservice.otpservice.modal;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("checklistmaster")
public class CheckListMaster {
    @Transient
    public static final String SEQUENCE_NAME = "checklistmaster_sequence";

    @Id
    private String id;
    private String checklistId;
    private String checklistName;
    private String checklistDescription;
    private String status;

}
