package com.cyberscout.awscrypto.core;


import java.time.LocalDate;


/**
 * Base interface defining higher-level cryptographic operations.
 *
 * @param <T> The type of the encrypted values
 */
public interface CryptoOperations<T> {

    T encryptString(String plainVal);

    String decryptString(T encryptedVal);

    T encryptLocalDate(LocalDate plainVal);

    LocalDate decryptLocalDate(T encryptedVal);
}
