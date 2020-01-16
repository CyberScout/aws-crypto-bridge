package com.cyberscout.awscrypto.core.aws;


import com.amazonaws.encryptionsdk.jce.JceMasterKey;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import static com.cyberscout.awscrypto.core.aws.CryptoTestUtils.createJceMasterKey;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


public class AwsCryptoByteArrayAdaptorTest {

    private AwsCryptoByteArrayAdaptor crypto;


    @Before
    public void createTestObjects() {

        JceMasterKey masterKey = createJceMasterKey(UUID.randomUUID().toString());
        AwsCryptoFacade<JceMasterKey> facade = AwsCryptoFacade
                .forMasterKeyProvider(masterKey)
                .addContext("Purpose", "Test")
                .build();
        this.crypto = new AwsCryptoByteArrayAdaptor(facade);
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
        byte[] encrypted = this.crypto.encryptString(plaintext);
        assertThat(encrypted, not(equalTo(plaintext)));
        String decrypted = this.crypto.decryptString(encrypted);
        assertThat(decrypted, equalTo(plaintext));
    }


    @Test(expected = NullPointerException.class)
    public void checkEncryptLocalDateWithNullParam() {

        this.crypto.encryptLocalDate(null);
    }


    @Test(expected = NullPointerException.class)
    public void checkDecryptLocalDateWithNullParam() {

        this.crypto.decryptLocalDate(null);
    }


    @Test
    public void checkLocalDateEncryption() {

        LocalDate plaintext = LocalDate.of(2020, Month.FEBRUARY, 2);
        byte[] encrypted = this.crypto.encryptLocalDate(plaintext);
        assertThat(encrypted, not(equalTo("2020-02-02")));
        LocalDate decrypted = this.crypto.decryptLocalDate(encrypted);
        assertThat(decrypted, equalTo(plaintext));
    }
}
