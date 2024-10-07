package com.appopay.visa.controller;

import com.appopay.visa.model.*;
import com.appopay.visa.service.DeviceService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("device/")
public class DeviceController {

    private static final Logger log = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private DeviceService deviceService;


    @PostMapping(value = "checkStatus")
    public ResponseEntity<ResponseDTO> checkDeviceBinding(@RequestBody DeviceEnquiryRequestDTO request) {
        log.info("checking status of: " + request.toString());
        String status = deviceService.getDeviceStatus(request.getDeviceId(), request.getMobileNo());
        log.info("returning status " + status);
        return ResponseEntity.ok().body(new ResponseDTO(status));
    }

    @PostMapping(value = "bind")
    public ResponseEntity<DeviceBindResponseDTO> bindDevice(@RequestBody DeviceEnquiryRequestDTO request) {
        log.info("binding device of: " + request.toString());
        String previousDeviceId = deviceService.bindDevice(request.getDeviceId(), request.getMobileNo(), null);
        return ResponseEntity.ok().body(new DeviceBindResponseDTO(previousDeviceId));
    }

    @PostMapping(value = "reBind")
    public ResponseEntity<DeviceBindResponseDTO> reBindDevice(@RequestBody DeviceEnquiryRequestDTO request) {
        log.info("re binding of: " + request.toString());
        String previousDeviceId = deviceService.reBindDevice(request.getDeviceId(), request.getMobileNo());
        return ResponseEntity.ok().body(new DeviceBindResponseDTO(previousDeviceId));
    }

    @PostMapping("/save-pin")
    public ResponseEntity<ResponseDTO> savePin(@Valid @RequestBody DevicePinSaveRequestDTO request) {
        log.info("saving pin of: " + request.toString());
        deviceService.savePin(request.getDeviceId(), request.getMobilePin());
        return ResponseEntity.ok(new ResponseDTO("Mobile PIN saved successfully"));
    }

    @PostMapping("/update-pin")
    public ResponseEntity<ResponseDTO> updatePin(@Valid @RequestBody DevicePinSaveRequestDTO request) {
        log.info("updating pin of: " + request.toString());
        deviceService.savePin(request.getDeviceId(), request.getMobilePin());
        return ResponseEntity.ok(new ResponseDTO("Mobile PIN updated successfully"));
    }

    @PostMapping("/change-pin")
    public ResponseEntity<ResponseDTO> changePin(@Valid @RequestBody DevicePinSaveRequestDTO request) {
        log.info("changePin pin of: " + request.toString());
        deviceService.verifyPin(request.getDeviceId(), request.getOldPin());
        deviceService.savePin(request.getDeviceId(), request.getMobilePin());
        return ResponseEntity.ok(new ResponseDTO("Mobile PIN updated successfully"));
    }

    @PostMapping("/verify-pin")
    public ResponseEntity<VerifyResponseDTO> verifyPin(@Valid @RequestBody DeviceVerifyPinRequestDTO request) {
        log.info("verifying pin of: " + request.toString());
        String response = deviceService.verifyPin(request.getDeviceId(), request.getMobilePin());
        log.info("verification response " + response);
        return ResponseEntity.ok(new VerifyResponseDTO(response));
    }

}
