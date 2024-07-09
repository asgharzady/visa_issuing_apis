package com.appopay.visa.repository;

import com.appopay.visa.entity.DeviceEntity;
import com.appopay.visa.entity.IamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

}
