package com.cyberscout.awscrypto.shim.jasypt;


import com.cyberscout.awscrypto.core.Base64StringCrypto;
import org.jasypt.encryption.StringEncryptor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


public class JasyptToAwsBase64Shim implements Base64StringCrypto {

    private final Base64StringCrypto cryptoDelegate;
    private final StringEncryptor legacyDecryptor;


    public JasyptToAwsBase64Shim(Base64StringCrypto cryptoDelegate, StringEncryptor legacyDecryptor) {

        this.cryptoDelegate = cryptoDelegate;
        this.legacyDecryptor = legacyDecryptor;
    }

    @Override
    public String encryptByteArray(byte[] plainVal) {

        return null;
    }


    @Override
    public byte[] decryptByteArray(String encryptedVal) {

        return new byte[0];
    }


    @Override
    public String encryptLong(long plainVal) {

        return null;
    }


    @Override
    public long decryptLong(String encryptedVal) {

        return 0;
    }


    @Override
    public String encryptInteger(int plainVal) {

        return null;
    }


    @Override
    public int decryptInteger(String encryptedVal) {

        return 0;
    }


    @Override
    public String encryptBoolean(boolean plainVal) {

        return null;
    }


    @Override
    public boolean decryptBoolean(String encryptedVal) {

        return false;
    }


    @Override
    public String encryptString(String plainVal) {

        return null;
    }


    @Override
    public String decryptString(String encryptedVal) {

        return null;
    }


    @Override
    public String encryptLocalDate(LocalDate plainVal) {

        return null;
    }


    @Override
    public LocalDate decryptLocalDate(String encryptedVal) {

        return null;
    }


    @Override
    public String encryptLocalDateTime(LocalDateTime plainVal) {

        return null;
    }


    @Override
    public LocalDateTime decryptLocalDateTime(String encryptedVal) {

        return null;
    }


    @Override
    public String encryptDate(Date plainVal) {

        return null;
    }


    @Override
    public Date decryptDate(String encryptedVal) {

        return null;
    }
}
