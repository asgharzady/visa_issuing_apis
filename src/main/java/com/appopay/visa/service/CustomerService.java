package com.appopay.visa.service;

import com.appopay.visa.entity.Customers;
import com.appopay.visa.entity.IamEntity;
import com.appopay.visa.exception.CustomException;
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
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDTO create(CustomerDTO customerDTO){
        if(customerDTO.getId()!=null){
            throw new CustomException("new customer can not have id");
        }
        else{
            return customerRepository.save(customerDTO.toEntity()).toDTO();
        }
    }

    public CustomerDTO updateOne(CustomerDTO customerDTO){
        if(customerDTO.getId()==null){
            throw new CustomException("please enter a customer id");
        }
        Optional<Customers> optionalCustomers = customerRepository.findById(customerDTO.getId());

        if(optionalCustomers.isEmpty()){
            throw new CustomException("customer not found");
        }
         Customers customers = optionalCustomers.get();

        if(customers.getId()!=null)
            customers.setId(customerDTO.getId());
        if(customers.getFistName()!=null)
            customers.setFistName(customerDTO.getFistName());
        if(customers.getLastName()!=null)
            customers.setLastName(customerDTO.getLastName());
        if(customers.getPhoneNumber()!=null)
            customers.setPhoneNumber(customerDTO.getPhoneNumber());
        if(customers.getEmail()!=null)
            customers.setEmail(customerDTO.getEmail());
        if(customers.getAddress()!=null)
            customers.setAddress(customerDTO.getAddress());
        if(customers.getGender()!=null)
            customers.setGender(customerDTO.getGender());
        if(customers.getToken()!=null)
            customers.setToken(customerDTO.getToken());

        return customerRepository.save(customers).toDTO();
    }

    public CustomerDTO getById(Long id) {
        Optional<Customers> customers = customerRepository.findById(id);
        if (customers.isPresent()) {
            return customers.get().toDTO();
        }
        throw new CustomException("Customer not found");
    }
}
