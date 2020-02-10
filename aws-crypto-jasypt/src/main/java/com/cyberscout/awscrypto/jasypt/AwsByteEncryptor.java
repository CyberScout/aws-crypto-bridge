package com.cyberscout.awscrypto.jasypt;


import com.cyberscout.awscrypto.core.ByteArrayCrypto;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.pbe.PBEByteEncryptor;


/**
 * Implementation of {@code PBEByteEncryptor} that delegates crypto operations to a {@code ByteArrayCrypto} object.
 * The encrypted data will thus be held in a byte array.
 */
@RequiredArgsConstructor
public class AwsByteEncryptor implements PBEByteEncryptor {

    private final ByteArrayCrypto cryptoDelegate;


    @Override
    public byte[] encrypt(byte[] message) {

        return this.cryptoDelegate.encryptByteArray(message);
    }


    @Override
    public byte[] decrypt(byte[] encryptedMessage) {

        return this.cryptoDelegate.decryptByteArray(encryptedMessage);
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
