package com.appopay.visa.controller;

import com.appopay.visa.model.DeviceEnquiryRequestDTO;
import com.appopay.visa.model.DevicePinSaveRequestDTO;
import com.appopay.visa.model.DeviceVerifyPinRequestDTO;
import com.appopay.visa.model.ResponseDTO;
import com.appopay.visa.service.DeviceService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ResponseDTO> checkDeviceBinding(@RequestBody DeviceEnquiryRequestDTO request) {
        String status = deviceService.getDeviceStatus(request.getDeviceId(),request.getUsername());
        return ResponseEntity.ok().body(new ResponseDTO(status));
    }

    @PostMapping(value = "bind")
    public ResponseEntity<ResponseDTO> bindDevice(@RequestBody DeviceEnquiryRequestDTO request) {
        String status = deviceService.bindDevice(request.getDeviceId(),request.getUsername(),null);
        return ResponseEntity.ok().body(new ResponseDTO(status));
    }

    @PostMapping(value = "reBind")
    public ResponseEntity<ResponseDTO> reBindDevice(@RequestBody DeviceEnquiryRequestDTO request) {
        String status = deviceService.reBindDevice(request.getDeviceId(),request.getUsername());
        return ResponseEntity.ok().body(new ResponseDTO(status));
    }

    @PostMapping("/save-pin")
    public ResponseEntity<ResponseDTO> savePin(@Valid @RequestBody DevicePinSaveRequestDTO request) {
        deviceService.savePin(request.getDeviceId(), request.getMobilePin());
        return ResponseEntity.ok(new ResponseDTO("Mobile PIN saved successfully"));
    }

    @PostMapping("/update-pin")
    public ResponseEntity<ResponseDTO> updatePin(@Valid @RequestBody DevicePinSaveRequestDTO request) {
        deviceService.savePin(request.getDeviceId(), request.getMobilePin());
        return ResponseEntity.ok(new ResponseDTO("Mobile PIN updated successfully"));
    }

    @PostMapping("/verify-pin")
    public ResponseEntity<ResponseDTO> verifyPin(@Valid @RequestBody DeviceVerifyPinRequestDTO request) {
        deviceService.verifyPin(request.getDeviceId(), request.getMobilePin());
        return ResponseEntity.ok(new ResponseDTO("Mobile PIN verified successfully"));
    }

}
