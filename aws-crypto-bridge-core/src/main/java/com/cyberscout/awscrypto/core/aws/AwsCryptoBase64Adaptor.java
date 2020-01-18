package com.cyberscout.awscrypto.core.aws;


import com.cyberscout.awscrypto.core.Base64StringCrypto;

import java.nio.charset.Charset;
import java.util.Objects;


/**
 * Implementation of {@link Base64StringCrypto} that delegates cryptographic operations to an {@link AwsCryptoFacade}.
 */
public class AwsCryptoBase64Adaptor extends BaseAwsCryptoOperations<String> implements Base64StringCrypto {

    public AwsCryptoBase64Adaptor(AwsCryptoFacade<?> cryptoFacade) {

        super(cryptoFacade);
    }


    @SuppressWarnings("unused")
    public AwsCryptoBase64Adaptor(AwsCryptoFacade<?> cryptoFacade, Charset charset) {

        super(cryptoFacade, charset);
    }


    @Override
    public String encryptByteArray(byte[] plainVal) {

        Objects.requireNonNull(plainVal, "plainVal");
        byte[] ciphertext = this.getCrypto().encryptData(plainVal);
        return BASE64_ENCODER.encodeToString(ciphertext);
    }


    @Override
    public byte[] decryptByteArray(String encryptedVal) {

        Objects.requireNonNull(encryptedVal, "encryptedVal");
        byte[] ciphertext = BASE64_DECODER.decode(encryptedVal);
        return this.getCrypto().decryptData(ciphertext);
    }


    @Override
    public String encryptString(String plainVal) {

        Objects.requireNonNull(plainVal, "plainVal");
        byte[] plaintext = plainVal.getBytes(this.getCharset());
        byte[] ciphertext = this.getCrypto().encryptData(plaintext);
        return BASE64_ENCODER.encodeToString(ciphertext);
    }


    @Override
    public String decryptString(String encryptedVal) {

        Objects.requireNonNull(encryptedVal, "encryptedVal");
        byte[] ciphertext = BASE64_DECODER.decode(encryptedVal);
        byte[] plaintext = this.getCrypto().decryptData(ciphertext);
        return new String(plaintext, this.getCharset());
    }
}
