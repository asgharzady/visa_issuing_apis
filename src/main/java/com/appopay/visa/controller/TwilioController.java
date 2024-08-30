package com.appopay.visa.controller;

import com.appopay.visa.model.*;
import com.appopay.visa.service.DeviceService;
import com.appopay.visa.service.TwillioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("twilio/")
public class TwilioController {

    @Autowired
    private TwillioService twillioService;


    @PostMapping(value = "getSms/")
    public ResponseEntity<TwillioResponseDTO> getSms(@RequestHeader("auth") String auth,
                                                     @RequestBody TwillioRequestDTO body
    ) {
        System.out.println("this");
        return ResponseEntity.ok().body(twillioService.sendSMS(auth, body));
    }
}
