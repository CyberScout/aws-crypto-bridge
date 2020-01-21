package com.cyberscout.awscrypto.core;


/**
 * @param <P> The type of the plaintext value
 * @param <E> The type of the encrypted value
 */
public interface CryptoOperation<P, E> {

    E encrypt(P plainValue);

    P decrypt(E encryptedValue);

    Class<P> getPlaintextDataType();

    Class<E> getEncryptedDataType();
}
