package com.cyberscout.awscrypto.core.aws;


import com.cyberscout.awscrypto.core.Base64StringCrypto;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Objects;


public class AwsCryptoBase64Adaptor implements Base64StringCrypto {

    private static final Base64.Decoder BASE64_DECODER = Base64.getMimeDecoder();
    private static final Base64.Encoder BASE64_ENCODER = Base64.getMimeEncoder();

    private final AwsCryptoFacade<?> crypto;
    private final Charset charset;


    public AwsCryptoBase64Adaptor(AwsCryptoFacade<?> cryptoFacade) {

        this(cryptoFacade, StandardCharsets.UTF_8);
    }


    public AwsCryptoBase64Adaptor(AwsCryptoFacade<?> cryptoFacade, Charset charset) {

        this.crypto = cryptoFacade;
        this.charset = charset;
    }


    @Override
    public String encryptString(String plainVal) {

        Objects.requireNonNull(plainVal, "plainVal");
        byte[] plaintext = plainVal.getBytes(this.charset);
        byte[] ciphertext = this.crypto.encryptData(plaintext);
        return new String(BASE64_ENCODER.encode(ciphertext), this.charset);
    }


    @Override
    public String decryptString(String encryptedVal) {

        Objects.requireNonNull(encryptedVal, "encryptedVal");
        byte[] ciphertext = BASE64_DECODER.decode(encryptedVal);
        byte[] plaintext = this.crypto.decryptData(ciphertext);
        return new String(plaintext, this.charset);
    }


    @Override
    public String encryptLocalDate(LocalDate plainVal) {

        return null;
    }


    @Override
    public LocalDate decryptLocalDate(String encryptedVal) {

        return null;
    }
}
