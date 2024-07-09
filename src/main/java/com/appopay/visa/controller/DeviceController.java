package com.appopay.visa.controller;

import com.appopay.visa.model.DeviceEnquiryRequestDTO;
import com.appopay.visa.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Instant;

@RestController
@RequestMapping("device/")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;


    @PostMapping(value = "checkStatus")
    public ResponseEntity<String> checkDeviceBinding(@RequestBody DeviceEnquiryRequestDTO request) {
        String status = deviceService.getDeviceStatus(request.getDeviceId(),request.getUsername());
        return ResponseEntity.ok().body(status);
    }

    @PostMapping(value = "bind")
    public ResponseEntity<String> bindDevice(@RequestBody DeviceEnquiryRequestDTO request) {
        String status = deviceService.bindDevice(request.getDeviceId(),request.getUsername(),null);
        return ResponseEntity.ok().body(status);
    }

    @PostMapping(value = "reBind")
    public ResponseEntity<String> reBindDevice(@RequestBody DeviceEnquiryRequestDTO request) {
        System.out.println(Instant.now());
        String status = deviceService.reBindDevice(request.getDeviceId(),request.getUsername());
        return ResponseEntity.ok().body(status);
    }


}
