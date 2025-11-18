package com.mylocalservices.app.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vendors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vendorName;
    private String email;
    private String phone;

    @Builder.Default
    private boolean approved = false;
}
