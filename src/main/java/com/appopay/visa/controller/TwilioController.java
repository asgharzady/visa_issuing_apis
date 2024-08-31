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


    @PostMapping("/sendOTP")
    public ResponseEntity<ResponseDTO> sendOTP(@RequestBody TwillioRequestDTO request) {
        return ResponseEntity.ok().body(new ResponseDTO(otpService.sendOTP(request)));
    }

    @GetMapping("/validateOTP/{toNumber}/{otp}")
    public ResponseEntity<ResponseDTO> validateOTP(@PathVariable("toNumber") String toNumber,@PathVariable("otp") String otp) {
        return ResponseEntity.ok().body(new ResponseDTO(otpService.validateOTP(toNumber,otp)));
    }
}
