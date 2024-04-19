package com.microservices.drivenzy.otpservice.otpservice.modal;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document("foodOrderDetails")
public class FoodOrderDetails {

    private String date;
    private String user;
    private Map<String,Double> foodItemWithQuantity;
    private String status;
    private String message;

    public void setFoodOrderList(FoodOrderDetails foodOrderDetails) {
        this.date = foodOrderDetails.date;
        this.user = foodOrderDetails.user;
        this.foodItemWithQuantity = foodOrderDetails.foodItemWithQuantity;
    }
}
