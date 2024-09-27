package com.percheski.mining.entities.model;

import com.percheski.mining.entities.enums.Currency;
import com.percheski.mining.entities.enums.Networks;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletAddresses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String walletAddress;

    @Enumerated(EnumType.STRING)
    private Networks networks;

    @Enumerated(EnumType.STRING)
    private Currency currency;
    private String amount;

    public WalletAddresses(String walletAddress, Networks networks, Currency currency, String amount) {
        this.walletAddress = walletAddress;
        this.networks = networks;
        this.currency = currency;
        this.amount = amount;
    }
}
