package com.cyberscout.awscrypto.jasypt.bridge;


import com.amazonaws.encryptionsdk.exception.AwsCryptoException;
import com.cyberscout.awscrypto.core.Base64StringCrypto;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.pbe.PBEStringEncryptor;


/**
 * Implementation of {@code PBEStringEncryptor} that delegates crypto operations to a {@code Base64StringCrypto}
 * object, but also handles decrypting "legacy" data. The encrypted data will be held in a Base64-encoded string.
 */
@RequiredArgsConstructor
public class JasyptAwsCryptoStringBridge implements PBEStringEncryptor {

    private final Base64StringCrypto cryptoDelegate;
    private final PBEStringEncryptor legacyDecryptor;


    @Override
    public String encrypt(String message) {

        return this.cryptoDelegate.encryptString(message);
    }


    @Override
    public String decrypt(String encryptedMessage) {

        try {
            return this.cryptoDelegate.decryptString(encryptedMessage);
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
