package com.mylocalservices.app.entity.service.provider.management.workers;

import com.mylocalservices.app.entity.auth.User;
import com.mylocalservices.app.entity.service.provider.management.workers.book.WorkerSchedule;
import com.mylocalservices.app.enums.auth.worker.ServiceType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "worker_profiles",
        indexes = {
                @Index(name = "idx_service_city", columnList = "serviceType, city"),
                @Index(name = "idx_available", columnList = "available")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Link to users table
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // 🔧 Service Details
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceType serviceType;

    private Integer experienceYears;

    @Column(length = 1000)
    private String description;

    // 📍 Location
    @Column(nullable = false)
    private String city;

    private String area;
    private String pincode;

    // 💰 Pricing
    @Column(nullable = false)
    private BigDecimal baseFare;

    // 📅 Quick availability flag
    @Column(nullable = false)
    @Builder.Default
    private Boolean available = true;

    // 🔗 All schedules
    @OneToMany(
            mappedBy = "worker",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<WorkerSchedule> schedules = new ArrayList<>();

    // 🕒 Audit
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Custom setters to ensure case consistency
    public void setCity(String city) {
        this.city = city != null ? city.toLowerCase() : null;
    }

    public void setArea(String area) {
        this.area = area != null ? area.toLowerCase() : null;
    }
}
