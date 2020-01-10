package com.cyberscout.awscrypto.core.aws;


import com.amazonaws.encryptionsdk.jce.JceMasterKey;
import lombok.SneakyThrows;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


public class CryptoTestUtils {

    public static final String PROVIDER_ID = "test";
    public static final int AES_KEY_SIZE = 256;
    public static final String ALGORITHM = "AES/GCM/NoPadding";


    @SneakyThrows
    public static SecretKey generateAesKey() {

        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(AES_KEY_SIZE);
        return keyGen.generateKey();
    }


    public static JceMasterKey createJceMasterKey(String keyId) {

        SecretKey key = generateAesKey();
        return JceMasterKey.getInstance(key, PROVIDER_ID, keyId, ALGORITHM);
    }
}
