package com.appopay.visa.service;

import com.appopay.visa.Exception.CustomException;
import com.appopay.visa.entity.Customers;
import com.appopay.visa.entity.DeviceEntity;
import com.appopay.visa.entity.IamEntity;
import com.appopay.visa.model.CustomerDTO;
import com.appopay.visa.model.IamDTO;
import com.appopay.visa.repository.CustomerRepository;
import com.appopay.visa.repository.DeviceRepository;
import com.appopay.visa.repository.IamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private IamRepository iamRepository;

    @Autowired
    private DeviceRepository deviceRepository;


    public String getDeviceStatus(String deviceId, String username) {

        Optional<IamEntity> iam = iamRepository.findByUserName(username);

        if (iam.isPresent()) {
            for (DeviceEntity deviceEntity : iam.get().getDevices()) {
                if (deviceEntity.getDeviceId().equals(deviceId)) {
                    return deviceEntity.getStatus();
                }
            }
        }
        return "device or username not found";
    }

    public String bindDevice(String deviceId, String username, String newStatus) {

        Optional<IamEntity> iamEntity = iamRepository.findByUserName(username);
        DeviceEntity device = new DeviceEntity();
        if (iamEntity.isPresent()) {
            IamEntity iam = iamEntity.get();
            for (DeviceEntity deviceEntity : iam.getDevices()) {
                String status = deviceEntity.getStatus();
                if (status.equals("ACTIVE") || status.equals("COOLING_OFF")) {
                    deviceEntity.setStatus("NOT_ACTIVE");
                }
                if (deviceEntity.getDeviceId().equals(deviceId)) {
                    device = deviceEntity;
                }
            }

            device.setDeviceId(deviceId);
            if(newStatus == null){
                newStatus = "ACTIVE";
            }
            device.setStatus(newStatus);

            iam.getDevices().add(device);
            iamRepository.save(iam);
            return "ok";
        }
        throw new UsernameNotFoundException("Username not found");
    }

    public String reBindDevice(String deviceId, String username) {
        bindDevice(deviceId,username,"COOLING_OFF");
        return "ok";
    }

    public void savePin(String deviceId, String mobilePin) {
//        DeviceEntity existingDevice = deviceRepository.findByMobilePin(mobilePin);
//        if (existingDevice != null && !existingDevice.getDeviceId().equals(deviceId)) {
//            throw new IllegalArgumentException("PIN already associated with another device");
//        }

        DeviceEntity device = deviceRepository.findByDeviceId(deviceId);
        if (device == null) {
            device = new DeviceEntity();
            device.setDeviceId(deviceId);
        }
        device.setMobilePin(mobilePin);
        deviceRepository.save(device);
    }

    public void verifyPin(String deviceId, String mobilePin) {
        DeviceEntity device = deviceRepository.findByDeviceId(deviceId);
        if(device == null){
            throw new CustomException("Invalid device id");
        }
        else if(!(device.getMobilePin().equals(mobilePin))){
            throw new CustomException("invalid mobile pin");
        }
    }
}
