package com.example.demo.controller;
import com.example.demo.domain.Customer;
import com.example.demo.domain.Url;
import com.example.demo.service.CustomerService;
import com.example.demo.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
public class CustomerController {

    private final CustomerService customerService;

//    @CrossOrigin
//    @PostMapping("/user")
//    public ResponseEntity<?> save(@RequestBody Customer customer){
//        System.out.println("Name : " + customer.getName());
//        System.out.println("Password : "  +customer.getPassword());
//        return new ResponseEntity<>(customerService.create(customer), HttpStatus.CREATED);
//    }

    @CrossOrigin
    @PostMapping("/login")
    //RequestBody automatically assigns JSON vars to object vars
    public ResponseEntity<String> login(@RequestBody Customer customer) {
        //authenticate provided information is correct for login
        boolean authenticated = customerService.authenticate(customer.getUserId(), customer.getPassword());
        System.out.println("====================USER LOGIN==================== ");
        System.out.println("userID: "  + customer.getUserId());
        System.out.println("Password: "  + customer.getPassword());
        if (authenticated) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }



}