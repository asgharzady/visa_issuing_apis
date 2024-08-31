package com.appopay.visa.controller;

import com.appopay.visa.model.*;
import com.appopay.visa.service.DeviceService;
import com.appopay.visa.service.OTPService;
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

    @Autowired
    private OTPService otpService;


    @PostMapping(value = "getSms/{toNumber}/{otp}")
    public ResponseEntity<TwillioResponseDTO> getSms(@PathVariable("toNumber") String toNumber,
                                                     @PathVariable("otp") String otp) {
        return ResponseEntity.ok().body(twillioService.sendSMS(toNumber,otp));
    }


    @GetMapping("/sendOTP/{toNumber}")
    public ResponseEntity<ResponseDTO> sendOTP(@PathVariable("toNumber") String toNumber) {
        return ResponseEntity.ok().body(new ResponseDTO(otpService.sendOTP(toNumber)));
    }

    @GetMapping("/validateOTP/{toNumber}/{otp}")
    public ResponseEntity<ResponseDTO> validateOTP(@PathVariable("toNumber") String toNumber,@PathVariable("otp") String otp) {
        return ResponseEntity.ok().body(new ResponseDTO(otpService.validateOTP(toNumber,otp)));
    }
}
