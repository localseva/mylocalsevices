package com.mylocalservices.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "service_workers")
@Data
@NoArgsConstructor
public class ServiceWorker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String workType;
    private double price;
    private String status;

    // getters & setters
}
