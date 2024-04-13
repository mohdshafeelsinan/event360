package com.microservices.drivenzy.otpservice.otpservice.modal;

import lombok.Data;

@Data
public class EventCheckList {
    private CheckListMaster checkListMaster = new CheckListMaster();
    private Boolean isCompleted;
}
