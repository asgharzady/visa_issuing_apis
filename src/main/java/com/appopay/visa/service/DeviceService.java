package com.appopay.visa.service;

import com.appopay.visa.exception.CustomException;
import com.appopay.visa.entity.DeviceEntity;
import com.appopay.visa.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public String getDeviceStatus(String deviceId, String mobileNo) {

        Optional<DeviceEntity> optionalDeviceEntity = deviceRepository.findByMobileNoAndDeviceId(mobileNo, deviceId);
        if (optionalDeviceEntity.isPresent()) {
            return optionalDeviceEntity.get().getStatus();
        }
        return "device or username not found";
    }

    public String bindDevice(String deviceId, String mobileNo, String newStatus) {

        Optional<DeviceEntity> optionalDeviceEntity = deviceRepository.findByMobileNo(mobileNo);
        DeviceEntity deviceEntity = optionalDeviceEntity.orElseGet(DeviceEntity::new);
        if (optionalDeviceEntity.isEmpty()) {
            deviceEntity.setMobileNo(mobileNo);
        }
        deviceEntity.setStatus(newStatus == null ? "ACTIVE" : newStatus);
        deviceEntity.setDeviceId(deviceId);
        deviceRepository.save(deviceEntity);
        return "ok";
    }

    public String reBindDevice(String deviceId, String mobileNo) {
        bindDevice(deviceId, mobileNo, "COOLING_OFF");
        return "ok";
    }

    public void savePin(String deviceId, String mobilePin) {

        DeviceEntity device = deviceRepository.findByDeviceId(deviceId);
        if (device == null) {
            throw new CustomException("Device id not found");
        }
        device.setMobilePin(mobilePin);
        deviceRepository.save(device);
    }

    public String verifyPin(String deviceId, String mobilePin) {
        DeviceEntity device = deviceRepository.findByDeviceId(deviceId);
        if (device == null) {
            throw new CustomException("Invalid device id");
        } else if (!(device.getMobilePin().equals(mobilePin))) {
            throw new CustomException("invalid mobile pin");
        }
        else {
            Instant currentTime = Instant.now().truncatedTo(ChronoUnit.SECONDS);
            device.setLastLoginTime(currentTime);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(java.time.ZoneOffset.UTC);
            String formattedTime = formatter.format(currentTime);
            deviceRepository.save(device);
            return formattedTime;
        }
    }
}
