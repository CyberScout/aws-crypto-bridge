package com.cyberscout.awscrypto.hibernate;


import java.util.Date;


public class DateToEncryptedStringType extends BaseMutableEncryptedStringType {

    @Override
    protected Object decryptValue(String encryptedVal) {

        return this.getCrypto().decryptDate(encryptedVal);
    }


    @Override
    protected String encryptValue(Object plainVal) {

        Date plaintext = (Date) plainVal;
        return this.getCrypto().encryptDate(plaintext);
    }
}
