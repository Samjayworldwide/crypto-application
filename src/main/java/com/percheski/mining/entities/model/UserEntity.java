package com.percheski.mining.entities.model;
import com.percheski.mining.entities.enums.Currency;
import com.percheski.mining.entities.enums.Gender;
import com.percheski.mining.entities.enums.Roles;
import com.percheski.mining.entities.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String dob;
    private String password;
    private String nationality;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    private boolean isVerified;

    private Long numberOfTimes;


   @OneToMany(mappedBy = "userEntity",cascade = CascadeType.ALL)
    private List<PaymentData> paymentData = new ArrayList<>();

    public void addImage(PaymentData paymentData1){
        paymentData.add(paymentData1);
        paymentData1.setUserEntity(this);
    }


}
