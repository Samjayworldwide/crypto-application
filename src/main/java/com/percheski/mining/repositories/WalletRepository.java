package com.percheski.mining.repositories;

import com.percheski.mining.entities.enums.Currency;
import com.percheski.mining.entities.enums.Networks;
import com.percheski.mining.entities.model.WalletAddresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<WalletAddresses, Long> {
    WalletAddresses findByCurrencyAndNetworks(Currency currency, Networks networks);
   List< WalletAddresses> findAllByCurrency(Currency currency);
}
