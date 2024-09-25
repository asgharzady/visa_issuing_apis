package com.appopay.visa.service;

import com.appopay.visa.exception.CustomException;
import com.appopay.visa.entity.DeviceEntity;
import com.appopay.visa.repository.DeviceRepository;
import com.appopay.visa.repository.IamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private IamRepository iamRepository;

    @Autowired
    private DeviceRepository deviceRepository;


//    public String getDeviceStatus(String deviceId, String username) {
//
//        Optional<IamEntity> iam = iamRepository.findByUserName(username);
//
//        if (iam.isPresent()) {
//            for (DeviceEntity deviceEntity : iam.get().getDevices()) {
//                if (deviceEntity.getDeviceId().equals(deviceId)) {
//                    return deviceEntity.getStatus();
//                }
//            }
//        }
//        return "device or username not found";
//    }
//
//    public String bindDevice(String deviceId, String username, String newStatus) {
//
//        Optional<IamEntity> iamEntity = iamRepository.findByUserName(username);
//        DeviceEntity device = new DeviceEntity();
//        if (iamEntity.isPresent()) {
//            IamEntity iam = iamEntity.get();
//            for (DeviceEntity deviceEntity : iam.getDevices()) {
//                String status = deviceEntity.getStatus();
//                if (status.equals("ACTIVE") || status.equals("COOLING_OFF")) {
//                    deviceEntity.setStatus("NOT_ACTIVE");
//                }
//                if (deviceEntity.getDeviceId().equals(deviceId)) {
//                    device = deviceEntity;
//                }
//            }
//
//            device.setDeviceId(deviceId);
//            if(newStatus == null){
//                newStatus = "ACTIVE";
//            }
//            device.setStatus(newStatus);
//
//            iam.getDevices().add(device);
//            iamRepository.save(iam);
//            return "ok";
//        }
//        throw new CustomException("Username not found");
//    }
//
//    public String reBindDevice(String deviceId, String username) {
//        bindDevice(deviceId,username,"COOLING_OFF");
//        return "ok";
//    }

    public String getDeviceStatus(String deviceId, String mobileNo) {

        Optional<DeviceEntity> optionalDeviceEntity = deviceRepository.findByMobileNoAndDeviceId(mobileNo,deviceId);
        if(optionalDeviceEntity.isPresent()){
            return optionalDeviceEntity.get().getStatus();
        }
        return "device or username not found";
    }

    public String bindDevice(String deviceId, String mobileNo, String newStatus) {

        List<DeviceEntity> list = deviceRepository.findByMobileNo(mobileNo);
        if (!list.isEmpty()) {
            for (DeviceEntity deviceEntity : list) {
                String status = deviceEntity.getStatus();
                if (status.equals("ACTIVE") || status.equals("COOLING_OFF")) {
                    deviceEntity.setStatus("NOT_ACTIVE");
                }
                deviceRepository.save(deviceEntity);
            }
        }
        Optional<DeviceEntity> optionalDeviceEntity = deviceRepository.findByMobileNoAndDeviceId(mobileNo, deviceId);
        DeviceEntity deviceEntity = new DeviceEntity();
        if (optionalDeviceEntity.isEmpty()) {
            deviceEntity.setDeviceId(deviceId);
            deviceEntity.setMobileNo(mobileNo);
        } else {
            deviceEntity = optionalDeviceEntity.get();
        }
        if (newStatus == null) {
            newStatus = "ACTIVE";
        }
        deviceEntity.setStatus(newStatus);

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
            device = new DeviceEntity();
            device.setDeviceId(deviceId);
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
