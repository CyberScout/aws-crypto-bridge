package com.cyberscout.awscrypto.core;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class CryptoRegistry {

    private static CryptoRegistry INSTANCE = new CryptoRegistry();

    private Map<String, Encryptor> encryptors = new ConcurrentHashMap<>();
    private Map<String, Decryptor> decryptors = new ConcurrentHashMap<>();


    private CryptoRegistry() {

    }


    public static CryptoRegistry instance() {

        return INSTANCE;
    }


    public void registerEncryptor(String name, Encryptor encryptor) {

        this.encryptors.put(name, encryptor);
    }


    public void registerDecryptor(String name, Decryptor decryptor) {

        this.decryptors.put(name, decryptor);
    }


    public Encryptor lookupEncryptor(String name) {

        if (!this.encryptors.containsKey(name)) {
            throw new CryptoBridgeConfigException(String.format("No registered encrypter named '%s'", name));
        }
        return this.encryptors.get(name);
    }


    public Decryptor lookupDecryptor(String name) {

        if (!this.decryptors.containsKey(name)) {
            throw new CryptoBridgeConfigException(String.format("No registered decrypter named '%s'", name));
        }
        return this.decryptors.get(name);
    }
}
