package com.example.demo.repository;

import com.example.demo.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

   Optional<Customer> findByUserId(String userId);
   //automatically implements method using spring data jpa based on name
   //this one is for login authentication
   Optional<Customer> findByUserIdAndPassword(String userId, String password);

}
