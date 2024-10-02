package com.appopay.visa.controller;

import com.appopay.visa.model.*;
import com.appopay.visa.service.CustomerService;
import com.appopay.visa.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("customer/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PutMapping
    public ResponseEntity<CustomerDTO> updateOne(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok().body(customerService.updateOne(customerDTO));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customer) {
        return ResponseEntity.ok().body(customerService.create(customer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(customerService.getById(id));
    }

    @PostMapping("/token")
    public  ResponseEntity<CustomerTokenDTO> upsert(@RequestBody CustomerTokenDTO req){
        return ResponseEntity.ok().body(customerService.upsert(req));
    }

    @GetMapping("/token/{id}")
    public ResponseEntity<CustomerTokenDTO> getToken(@PathVariable String id){
        return ResponseEntity.ok().body(customerService.getToken(id));
    }

}
