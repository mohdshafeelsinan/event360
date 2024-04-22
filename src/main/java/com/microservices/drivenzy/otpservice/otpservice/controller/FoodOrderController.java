package com.microservices.drivenzy.otpservice.otpservice.controller;

import com.microservices.drivenzy.otpservice.otpservice.modal.CommonResponse;
import com.microservices.drivenzy.otpservice.otpservice.modal.FoodOrderDetails;
import com.microservices.drivenzy.otpservice.otpservice.service.FoodOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/foodOrder")
public class FoodOrderController {

    @Autowired
    private FoodOrderService foodOrderService;

    @PostMapping("/placeorder")
    public ResponseEntity<FoodOrderDetails> saveOrder(@RequestBody FoodOrderDetails orderList){
        FoodOrderDetails response = new FoodOrderDetails();
        try {
            response.setFoodOrderList(foodOrderService.saveOrderDetails(orderList));
            response.setStatus("SUCCESS");
            response.setMessage("Order placed Successfully");
        }catch (Exception e) {
            // Handle the error here
            e.printStackTrace();
            // You can also throw a custom exception or return an error response
            response.setStatus("FAILURE");
            response.setMessage("Couldn't place order");

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/getDayOrder")
    public CommonResponse getDayOrder(@RequestParam("date") String date){
        try{
            return new CommonResponse("Order details fetched successfully", "success", foodOrderService.getAllOrders(date));
        } catch (Exception e) {
            return new CommonResponse("Failed to get food order details", "failed", null);
        }
    }

    @PostMapping("/vendorOrderDetails")
    public CommonResponse getVendorOrder(@RequestParam("date") String date){
        try {
            return new CommonResponse("Vendor Order details fetched successfully","success", foodOrderService.getVendorOrderDetails(date));
        }catch (Exception e){
            return new CommonResponse("Failed to get vendor food order details", "failed", null);
        }
    }
    @PostMapping("/OrderItemList")
    public CommonResponse getOrderItemList(@RequestParam("date") String date){
        try {
            return new CommonResponse("Order Item List details fetched successfully","success", foodOrderService.getListOfOrders(date));
        }catch (Exception e){
            return new CommonResponse("Failed to get Order Item List details", "failed", null);
        }
    }
}
