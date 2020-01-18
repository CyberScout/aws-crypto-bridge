package com.cyberscout.awscrypto.core.aws;


import com.cyberscout.awscrypto.core.ByteArrayCrypto;

import java.nio.charset.Charset;
import java.util.Objects;


/**
 * Implementation of {@link ByteArrayCrypto} that delegates cryptographic operations to an {@link AwsCryptoFacade}.
 */
public class AwsCryptoByteArrayAdaptor extends BaseAwsCryptoOperations<byte[]> implements ByteArrayCrypto {

    public AwsCryptoByteArrayAdaptor(AwsCryptoFacade<?> cryptoFacade) {

        super(cryptoFacade);
    }


    @SuppressWarnings("unused")
    public AwsCryptoByteArrayAdaptor(AwsCryptoFacade<?> cryptoFacade, Charset charset) {

        super(cryptoFacade, charset);
    }


    @Override
    public byte[] encryptByteArray(byte[] plainVal) {

        Objects.requireNonNull(plainVal, "plainVal");
        return this.getCrypto().encryptData(plainVal);
    }


    @Override
    public byte[] decryptByteArray(byte[] encryptedVal) {

        Objects.requireNonNull(encryptedVal, "encryptedVal");
        return this.getCrypto().decryptData(encryptedVal);
    }


    @Override
    public byte[] encryptString(String plainVal) {

        Objects.requireNonNull(plainVal, "plainVal");
        return this.encryptByteArray(plainVal.getBytes(this.getCharset()));
    }


    @Override
    public String decryptString(byte[] encryptedVal) {

        return new String(this.decryptByteArray(encryptedVal), this.getCharset());
    }
}
