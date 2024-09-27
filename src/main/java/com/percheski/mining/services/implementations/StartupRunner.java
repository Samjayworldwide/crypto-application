package com.percheski.mining.services.implementations;

import com.percheski.mining.entities.enums.Currency;
import com.percheski.mining.entities.enums.Networks;
import com.percheski.mining.entities.model.WalletAddresses;
import com.percheski.mining.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupRunner implements CommandLineRunner {

    private final WalletRepository walletRepository;

    @Override
    public void run(String... args) throws Exception {

        long num = walletRepository.count();
        if(num < 7){
            walletRepository.save(new WalletAddresses("Thsahefededede", Networks.BITCOIN, Currency.BITCOIN,"10L"));
            walletRepository.save(new WalletAddresses("Thsfefsddcdc", Networks.BNB_BEACON_CHAIN, Currency.ETHEREUM, "2L"));
            walletRepository.save(new WalletAddresses("vwdfwedcahefededede", Networks.ETHEREUM, Currency.ETHEREUM, "45L"));
            walletRepository.save(new WalletAddresses("rwfwdvsfededede", Networks.ETHEREUM, Currency.BITCOIN, "89L"));
            walletRepository.save(new WalletAddresses("Tvwdvdvcsededede", Networks.TRON, Currency.USDT, "34L"));
            walletRepository.save(new WalletAddresses("2f2efcdcfededede", Networks.BNB_BEACON_CHAIN, Currency.USDT, "23L"));
            walletRepository.save(new WalletAddresses("2e2fewfededede", Networks.BNB_SMART_CHAIN, Currency.USDT, "78L"));

        }

    }
}