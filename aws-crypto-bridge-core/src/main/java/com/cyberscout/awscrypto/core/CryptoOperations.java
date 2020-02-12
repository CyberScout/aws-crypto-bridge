package com.cyberscout.awscrypto.core;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * Base interface defining higher-level cryptographic operations. Implementors will manage encrypting the plaintext
 * value, converting it (if necessary) to the common encrypted representation, and then reversing the process for
 * decrypting the data back into plaintext values. The goal is to make sure that the same configuration and logic is
 * used for both encryption and decryption. In practice, there are probably a finite set of types that make sense to
 * use for the encrypted values: strings, byte arrays, byte buffers, streams, and the like.
 *
 * @param <T> The type of the encrypted values
 */
// TODO Perhaps this can be broken down further?
// This pattern will quickly hit a wall, because there are only so many plaintext types we can support here. A more
// flexible structure would be to have an even more generic interface, one that is parameterized by plaintext type
// and ciphertext type. Such an interface would only need two operations -- encrypt & decrypt.
//
// For our immediate purposes, this structure is fine. But if this project grows and needs to support more use cases,
// then the more generic route would probably be required.
public interface CryptoOperations<T> {

    /**
     * Encrypt a byte array value.
     *
     * @param plainVal The plaintext value
     * @return The encrypted value
     */
    T encryptByteArray(byte[] plainVal);

    /**
     * Decrypt data and return the raw plaintext bytes.
     *
     * @param encryptedVal The encrypted data
     * @return The plaintext value
     */
    byte[] decryptByteArray(T encryptedVal);


    T encryptLong(long plainVal);

    long decryptLong(T encryptedVal);

    T encryptInteger(int plainVal);

    int decryptInteger(T encryptedVal);

    // short?
    // byte?
    // char?
    // char[]?
    // double?
    // float?

    T encryptBoolean(boolean plainVal);

    boolean decryptBoolean(T encryptedVal);

    // enum?

    /**
     * Encrypt a string value.
     *
     * @param plainVal The plaintext value
     * @return The encrypted value
     */
    T encryptString(String plainVal);

    /**
     * Decrypt data and convert it back into a plaintext string.
     *
     * @param encryptedVal The encrypted data
     * @return The plaintext value
     */
    String decryptString(T encryptedVal);

    /**
     * Encrypt a {@code LocalDate} value.
     *
     * @param plainVal The plaintext value
     * @return The encrypted value
     */
    T encryptLocalDate(LocalDate plainVal);

    /**
     * Decrypt data and convert it back into a plaintext {@code LocalDate} value.
     *
     * @param encryptedVal The encrypted data
     * @return The plaintext value
     */
    LocalDate decryptLocalDate(T encryptedVal);

    T encryptLocalDateTime(LocalDateTime plainVal);

    LocalDateTime decryptLocalDateTime(T encryptedVal);

    T encryptDate(Date plainVal);

    Date decryptDate(T encryptedVal);
}
