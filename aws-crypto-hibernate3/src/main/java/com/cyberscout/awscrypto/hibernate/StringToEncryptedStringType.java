package com.cyberscout.awscrypto.hibernate;


public class StringToEncryptedStringType extends BaseImmutableEncryptedStringType {

    @Override
    protected Object decryptValue(String encryptedVal) {

        return this.getCrypto().decryptString(encryptedVal);
    }


    @Override
    protected String encryptValue(Object plainVal) {

        String plaintext = (String) plainVal;
        return this.getCrypto().encryptString(plaintext);
    }
}
