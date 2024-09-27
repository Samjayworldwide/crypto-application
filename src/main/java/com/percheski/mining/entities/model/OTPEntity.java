package com.percheski.mining.entities.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OTPEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

    @Column(nullable = false)
   private int otp;
    @Column(nullable = false)
   private Date expirationDate;

    @CreationTimestamp
    private Date createdAt;

    private String email;


}
