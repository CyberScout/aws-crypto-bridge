package com.cyberscout.awscrypto.core.aws;


import com.amazonaws.encryptionsdk.exception.BadCiphertextException;
import com.amazonaws.encryptionsdk.jce.JceMasterKey;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import static com.cyberscout.awscrypto.core.aws.test.CryptoTestUtils.createJceMasterKey;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;


/**
 * Test harness to ensure that the {@link AwsCryptoFacade} performs encryption and decryption operations correctly,
 * namely, that they are transitive and self-verifying.
 */
public class AwsCryptoFacadeEncryptionTest {

    String keyId = UUID.randomUUID().toString();
    JceMasterKey masterKey;
    private byte[] plaintext = new byte[2048];


    @Before
    public void initializeEncryptionPrimitives() {

        Random rnd = new Random(System.currentTimeMillis());
        rnd.nextBytes(this.plaintext);
        this.masterKey = createJceMasterKey(this.keyId);
    }


    @Test
    public void checkDataEncryption() {

        AwsCryptoFacade<JceMasterKey> crypto = AwsCryptoFacade.forMasterKeyProvider(masterKey).build();
        byte[] encryptedData = crypto.encryptData(this.plaintext);
        assertFalse(Arrays.equals(encryptedData, this.plaintext));
        byte[] decryptedData = crypto.decryptData(encryptedData);
        assertArrayEquals(this.plaintext, decryptedData);
    }


    @Test
    public void checkDataEncryptionWithContext() {

        AwsCryptoFacade<JceMasterKey> crypto = AwsCryptoFacade
                .forMasterKeyProvider(this.masterKey)
                .addContext("Purpose", "Test")
                .addContext("Status", "Funky")
                .build();
        byte[] encryptedData = crypto.encryptData(this.plaintext);
        assertFalse(Arrays.equals(encryptedData, this.plaintext));
        byte[] decryptedData = crypto.decryptData(encryptedData);
        assertArrayEquals(this.plaintext, decryptedData);
    }


    @Test(expected = BadCiphertextException.class)
    public void checkDecryptionWithDifferentContext() {

        AwsCryptoFacade<JceMasterKey> encryptor = AwsCryptoFacade
                .forMasterKeyProvider(this.masterKey)
                .addContext("Purpose", "Test")
                .addContext("Status", "Funky")
                .build();
        byte[] encryptedData = encryptor.encryptData(this.plaintext);

        AwsCryptoFacade<JceMasterKey> decryptor = AwsCryptoFacade
                .forMasterKeyProvider(this.masterKey)
                .addContext("Purpose", "Production")
                .addContext("Status", "Untouchable")
                .build();
        decryptor.decryptData(encryptedData);
        // Exception should be thrown because the context doesn't match, even though the key is the same
    }
}
