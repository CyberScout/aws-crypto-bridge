package com.cyberscout.awscrypto.jasypt.bridge;


import com.amazonaws.encryptionsdk.exception.AwsCryptoException;
import com.cyberscout.awscrypto.core.ByteArrayCrypto;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.pbe.PBEByteEncryptor;


/**
 * Implementation of {@code PBEByteEncryptor} that delegates crypto operations to a {@code ByteArrayCrypto}
 * object, but also handles decrypting "legacy" data. The encrypted data will be held in a byte array.
 */
@RequiredArgsConstructor
public class JasyptAwsCryptoByteBridge implements PBEByteEncryptor {

    private final ByteArrayCrypto cryptoDelegate;
    private final PBEByteEncryptor legacyDecryptor;


    @Override
    public byte[] encrypt(byte[] message) {

        return this.cryptoDelegate.encryptByteArray(message);
    }


    @Override
    public byte[] decrypt(byte[] encryptedMessage) {

        try {
            return this.cryptoDelegate.decryptByteArray(encryptedMessage);
        }
        catch (AwsCryptoException e) {
            return this.legacyDecryptor.decrypt(encryptedMessage);
        }
    }


    @Override
    public void setPassword(String password) {

        this.legacyDecryptor.setPassword(password);
    }
}
