package com.appopay.visa.service;

import com.appopay.visa.entity.Customers;
import com.appopay.visa.entity.IamEntity;
import com.appopay.visa.model.CustomerDTO;
import com.appopay.visa.model.IamDTO;
import com.appopay.visa.repository.CustomerRepository;
import com.appopay.visa.repository.IamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OFACService {


    public boolean ofacApproved(String username) {
        return true;
    }


}
