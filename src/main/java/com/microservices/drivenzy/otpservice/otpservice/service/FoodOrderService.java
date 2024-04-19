package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.dto.FoodOrderDetailsDto;
import com.microservices.drivenzy.otpservice.otpservice.modal.Employees;
import com.microservices.drivenzy.otpservice.otpservice.modal.FoodOrderDetails;
import com.microservices.drivenzy.otpservice.otpservice.modal.LeaveTracker;
import com.microservices.drivenzy.otpservice.otpservice.repository.FoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FoodOrderService {
    @Autowired
    private MongoTemplate mongoTemplate;
    private FoodOrderRepository foodOrderRepository;

    public FoodOrderService(FoodOrderRepository foodOrderRepository){this.foodOrderRepository = foodOrderRepository;}

    public List<FoodOrderDetails> getAllOrders(String date) {
        List<FoodOrderDetails> response = foodOrderRepository.findByDate(date);
        return response;
    }

    public FoodOrderDetails saveOrderDetails(FoodOrderDetails foodOrderDetails) {
        try {
            return foodOrderRepository.save(foodOrderDetails);
        } catch (Exception e) {
            // Handle the exception here
            e.printStackTrace();
            return null; // Or throw a custom exception
        }
    }

    public Map<String, Double> getVendorOrderDetails(String date){
        List<FoodOrderDetails> foodOrderDetailsList = getAllOrders(date);
        Map<String, Double> distinctFoodItems = new HashMap<>();

        for (FoodOrderDetails order : foodOrderDetailsList) {
            for (Map.Entry<String, Double> entry : order.getFoodItemWithQuantity().entrySet()) {
                String foodItem = entry.getKey();
                double qty = entry.getValue();
                if (distinctFoodItems.containsKey(foodItem)) {
                    qty += distinctFoodItems.get(foodItem);
                }
                distinctFoodItems.put(foodItem, qty);
            }
        }

        return distinctFoodItems;
    }

    public List<FoodOrderDetailsDto> getListOfOrders(String date){
        List<FoodOrderDetails> foodOrderDetailsList = getAllOrders(date);
        List<FoodOrderDetailsDto> diffusedOrderList = new ArrayList<>();

        for (FoodOrderDetails orderDetails : foodOrderDetailsList){
            for (Map.Entry<String, Double> entry : orderDetails.getFoodItemWithQuantity().entrySet()) {
                FoodOrderDetailsDto foodOrderDetailsDto = new FoodOrderDetailsDto();
                foodOrderDetailsDto.setUname(orderDetails.getUser());
                foodOrderDetailsDto.setFoodItem(entry.getKey());
                foodOrderDetailsDto.setQty(entry.getValue());
                diffusedOrderList.add(foodOrderDetailsDto);
            }
        }

        return diffusedOrderList;
    }
}
