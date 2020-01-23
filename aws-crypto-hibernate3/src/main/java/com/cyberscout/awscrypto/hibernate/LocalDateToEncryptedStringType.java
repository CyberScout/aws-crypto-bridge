package com.cyberscout.awscrypto.hibernate;


import java.time.LocalDate;


public class LocalDateToEncryptedStringType extends BaseImmutableEncryptedStringType {

    @Override
    protected Object decryptValue(String encryptedVal) {

        return this.getCrypto().decryptLocalDate(encryptedVal);
    }


    @Override
    protected String encryptValue(Object plainVal) {

        LocalDate plaintext = (LocalDate) plainVal;
        return this.getCrypto().encryptLocalDate(plaintext);
    }
}
