package com.percheski.mining.services.implementations;

import com.percheski.mining.entities.enums.Currency;
import com.percheski.mining.entities.enums.Networks;
import com.percheski.mining.entities.model.WalletAddresses;
import com.percheski.mining.payload.request.WalletRequest;
import com.percheski.mining.payload.response.WalletResponse;
import com.percheski.mining.repositories.WalletRepository;
import com.percheski.mining.services.WalletServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class WalletImplementation implements WalletServices {

    private final WalletRepository walletRepository;

    public WalletResponse viewCoinWallet(WalletRequest request)  {
      WalletAddresses wallet = walletRepository.findByCurrencyAndNetworks(Currency.valueOf(request.getCurrency().toUpperCase()),
                Networks.valueOf(request.getNetworks().toUpperCase()));


      return WalletResponse.builder()
              .walletAddress(wallet.getWalletAddress())
              .amount(wallet.getAmount())
              .coin(String.valueOf(wallet.getCurrency()))
              .network(String.valueOf(wallet.getNetworks()))
              .build();
    }


//    private byte[] generateQrCode(String address) throws WriterException, IOException {
//
//        Map<EncodeHintType, Object> hints =new HashMap<>();
//        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        BitMatrix bitMatrix = qrCodeWriter.encode(address, BarcodeFormat.QR_CODE,500,500,hints);
//        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(bufferedImage,"png",baos);
//        return baos.toByteArray();
//    }
}
