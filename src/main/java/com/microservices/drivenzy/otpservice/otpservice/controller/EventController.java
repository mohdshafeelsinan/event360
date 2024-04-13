package com.microservices.drivenzy.otpservice.otpservice.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.microservices.drivenzy.otpservice.otpservice.dto.EventResponse;
import com.microservices.drivenzy.otpservice.otpservice.service.EventFormService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservices.drivenzy.otpservice.otpservice.modal.EventForm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class EventController {

    @Autowired
    private EventFormService eventFormService;

    @PostMapping("/event")
    public ResponseEntity<EventResponse> saveEventForm(@RequestBody EventForm eventForm) {
        EventResponse response = new EventResponse();
        try {
            response = eventFormService.saveEventForm(eventForm);
        } catch (Exception e) {
            response.setMessege("Error In Request");
            response.setStatus("FAILURE");
        }
        return new ResponseEntity<EventResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventForm>> getAllEvents() {
        List<EventForm> events = eventFormService.getAllEvents();
        return new ResponseEntity<List<EventForm>>(events, HttpStatus.OK);
    }


    @GetMapping("/event")
    public ResponseEntity<EventForm> getEvent(@RequestParam("id") Optional<String> id, @RequestParam("name") Optional<String> name) {
        if (id.isPresent()) {
            String eventId = id.get();
            EventForm event = eventFormService.getEventById(eventId);
            if (event != null) {
                return new ResponseEntity<EventForm>(event, HttpStatus.OK);
            } else {
                return new ResponseEntity<EventForm>(HttpStatus.NOT_FOUND);
            }
        } else if (name.isPresent()) {
            String eventName = name.get();
            EventForm event = eventFormService.getEventByName(eventName);
            if (event != null) {
                return new ResponseEntity<EventForm>(event, HttpStatus.OK);
            } else {
                return new ResponseEntity<EventForm>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<EventForm>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/events/today")
    public ResponseEntity<List<EventForm>> getEventsToday() {
        List<EventForm> eventsToday = eventFormService.getEventsToday();
        return new ResponseEntity<List<EventForm>>(eventsToday, HttpStatus.OK);
    }

        @GetMapping("/qrcode")
        public ResponseEntity<String> generateQRCode() {
            try {
                // Generate QR code
                String data = "https://www.google.com";
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);

                // Convert QR code to image
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
                byte[] imageBytes = outputStream.toByteArray();

                // Convert image to Base64-encoded string
                String base64Image = Base64.encodeBase64String(imageBytes);

                return ResponseEntity.ok(base64Image);
            } catch (WriterException | IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
    }


}
