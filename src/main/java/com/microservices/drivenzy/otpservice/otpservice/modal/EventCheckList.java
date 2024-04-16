package com.microservices.drivenzy.otpservice.otpservice.modal;

import lombok.Data;

@Data
public class EventCheckList {
//    private CheckList checkList = new CheckList();
    private Boolean isCompleted;
    private String checklistDescription;
}
