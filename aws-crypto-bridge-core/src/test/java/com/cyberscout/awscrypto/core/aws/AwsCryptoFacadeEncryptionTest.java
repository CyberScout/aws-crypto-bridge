package com.cyberscout.awscrypto.core.aws;


import com.amazonaws.encryptionsdk.jce.JceMasterKey;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;


/**
 * Test harness to ensure that the {@link AwsCryptoFacade} performs encryption and decryption operations correctly,
 * namely, that they are transitive and self-verifying.
 */
public class AwsCryptoFacadeEncryptionTest {

    private static final String PROVIDER_ID = "test";
    private static final int AES_KEY_SIZE = 256;

    JceMasterKey masterKey;
    String keyId = UUID.randomUUID().toString();
    AwsCryptoFacade<JceMasterKey> crypto;
    private byte[] plaintext = new byte[2048];


    @Before
    public void initializeObjectsAndData() {

        Random rnd = new Random(System.currentTimeMillis());
        rnd.nextBytes(this.plaintext);
        SecretKey secretKey = createSecretKey();
        this.masterKey = JceMasterKey.getInstance(secretKey, PROVIDER_ID, this.keyId, "AES/GCM/NoPadding");
        this.crypto = AwsCryptoFacade.forMasterKeyProvider(this.masterKey).build();
    }


    @Test
    public void checkDataEncryption() {

        byte[] encryptedData = this.crypto.encryptData(plaintext);
        assertFalse(Arrays.equals(encryptedData, plaintext));
        byte[] decryptedData = this.crypto.decryptData(encryptedData);
        assertArrayEquals(plaintext, decryptedData);
    }


    @SneakyThrows
    private SecretKey createSecretKey() {

        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(AES_KEY_SIZE);
        return keyGen.generateKey();
    }
}
