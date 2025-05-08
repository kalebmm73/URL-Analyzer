package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Url {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    //change from url_id to urlId so repository can find it
    private Long urlId;
    private String name;
    private String url;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    //each URL has one associated customer
    private Customer customer;


}
