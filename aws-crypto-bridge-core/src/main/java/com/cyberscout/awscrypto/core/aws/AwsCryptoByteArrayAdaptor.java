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
    public byte[] encryptString(String plainVal) {

        Objects.requireNonNull(plainVal, "plainVal");
        byte[] plaintext = plainVal.getBytes(this.getCharset());
        return this.getCrypto().encryptData(plaintext);
    }


    @Override
    public String decryptString(byte[] encryptedVal) {

        Objects.requireNonNull(encryptedVal, "encryptedVal");
        byte[] plaintext = this.getCrypto().decryptData(encryptedVal);
        return new String(plaintext, this.getCharset());
    }
}
