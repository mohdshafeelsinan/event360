package com.microservices.drivenzy.otpservice.otpservice.dto;

import lombok.Data;

@Data
public class FoodOrderDetailsDto {
    private String uname;
    private String foodItem;
    private double qty;
}
