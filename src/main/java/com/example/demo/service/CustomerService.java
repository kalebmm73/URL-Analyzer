package com.example.demo.service;


import com.example.demo.domain.Customer;
import com.example.demo.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer create(Customer customer){
        //save customer to database
        return customerRepository.save(customer);
    }

    public boolean authenticate(String userId, String password) {
        //set customer object to one matching userId and password
        Optional<Customer> customer = customerRepository.findByUserIdAndPassword(userId, password);
        //return true value if customer is present in database
        return customer.isPresent();
    }
}
