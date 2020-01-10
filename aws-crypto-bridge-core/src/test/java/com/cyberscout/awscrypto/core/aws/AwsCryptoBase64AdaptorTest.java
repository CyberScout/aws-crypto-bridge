package com.cyberscout.awscrypto.core.aws;


import com.amazonaws.encryptionsdk.jce.JceMasterKey;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static com.cyberscout.awscrypto.core.aws.CryptoTestUtils.createJceMasterKey;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


public class AwsCryptoBase64AdaptorTest {

    private AwsCryptoFacade<JceMasterKey> facade;
    private AwsCryptoBase64Adaptor crypto;


    @Before
    public void createTestObjects() {

        JceMasterKey masterKey = createJceMasterKey(UUID.randomUUID().toString());
        this.facade = AwsCryptoFacade.forMasterKeyProvider(masterKey).addContext("Purpose", "Test").build();
        this.crypto = new AwsCryptoBase64Adaptor(this.facade);
    }


    @Test(expected = NullPointerException.class)
    public void checkEncryptStringWithNullParam() {

        this.crypto.encryptString(null);
    }


    @Test(expected = NullPointerException.class)
    public void checkDecryptStringWithNullParam() {

        this.crypto.decryptString(null);
    }


    @Test
    public void checkStringEncryption() {

        String plaintext = "Awww, yeah!";
        String encrypted = this.crypto.encryptString(plaintext);
        assertThat(encrypted, not(equalTo(plaintext)));
        String decrypted = this.crypto.decryptString(encrypted);
        assertThat(decrypted, equalTo(plaintext));
    }
}
