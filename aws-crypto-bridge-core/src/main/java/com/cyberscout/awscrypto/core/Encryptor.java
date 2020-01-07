package com.cyberscout.awscrypto.core;


public interface Encryptor {

    byte[] encryptAsBytes(String plaintext);

    String encryptAsBase64(String plaintext);
}
