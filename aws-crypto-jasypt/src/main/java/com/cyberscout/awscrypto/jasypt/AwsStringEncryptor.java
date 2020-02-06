package com.cyberscout.awscrypto.jasypt;


import com.cyberscout.awscrypto.core.Base64StringCrypto;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.pbe.PBEStringEncryptor;


/**
 * Implementation of {@code PBEStringEncryptor} that delegates crypto operations to a {@code Base64StringCrypto} object.
 * The encrypted data will thus be held in a Base64-encoded string.
 */
@RequiredArgsConstructor
public class AwsStringEncryptor implements PBEStringEncryptor {

    private final Base64StringCrypto cryptoDelegate;


    @Override
    public String encrypt(String message) {

        return this.cryptoDelegate.encryptString(message);
    }


    @Override
    public String decrypt(String encryptedMessage) {

        return this.cryptoDelegate.decryptString(encryptedMessage);
    }


    /**
     * No-op. Support the contract so it can be plugged into some of the existing Jasypt integrations, like for
     * Hibernate.
     *
     * @param password Not used
     */
    @Override
    public void setPassword(String password) {

        // No-op
    }
}
