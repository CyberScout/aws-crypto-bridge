package com.cyberscout.awscrypto.core.aws;


import com.amazonaws.encryptionsdk.jce.JceMasterKey;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

import static com.cyberscout.awscrypto.core.aws.CryptoTestUtils.createJceMasterKey;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


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
    public void checkEncryptByteArrayWithNullParam() {

        this.crypto.encryptByteArray(null);
    }


    @Test(expected = NullPointerException.class)
    public void checkDecryptByteArrayWithNullParam() {

        this.crypto.decryptByteArray(null);
    }


    @Test
    public void checkByteArrayEncryption() {

        byte[] plaintext = new byte[] {
                0xB, 0xA, 0xD, 0xF, 0xA, 0xC, 0xE
        };
        byte[] encrypted = this.crypto.encryptByteArray(plaintext);
        assertThat(encrypted, not(equalTo(plaintext)));
        byte[] decrypted = this.crypto.decryptByteArray(encrypted);
        assertThat(decrypted, equalTo(plaintext));
    }


    @Test
    public void checkLongEncryption() {

        long plaintext = 4_8_15_16_23_42L;
        byte[] encrypted = this.crypto.encryptLong(plaintext);
        assertThat(encrypted, not(equalTo(plaintext)));
        long decrypted = this.crypto.decryptLong(encrypted);
        assertThat(decrypted, equalTo(plaintext));
    }


    @Test
    public void checkIntegerEncryption() {

        int plaintext = 8_15_16_23_42;
        byte[] encrypted = this.crypto.encryptInteger(plaintext);
        assertThat(encrypted, not(equalTo(plaintext)));
        int decrypted = this.crypto.decryptInteger(encrypted);
        assertThat(decrypted, equalTo(plaintext));
    }


    @Test
    public void checkBooleanEncryption() {

        byte[] encrypted = this.crypto.encryptBoolean(true);
        boolean decrypted = this.crypto.decryptBoolean(encrypted);
        assertTrue(decrypted);

        encrypted = this.crypto.encryptBoolean(false);
        decrypted = this.crypto.decryptBoolean(encrypted);
        assertFalse(decrypted);
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
        LocalDate decrypted = this.crypto.decryptLocalDate(encrypted);
        assertThat(decrypted, equalTo(plaintext));
    }


    @Test(expected = NullPointerException.class)
    public void checkEncryptLocalDateTimeWithNullParam() {

        this.crypto.encryptLocalDateTime(null);
    }


    @Test(expected = NullPointerException.class)
    public void checkDecryptLocalDateTimeWithNullParam() {

        this.crypto.decryptLocalDateTime(null);
    }


    @Test
    public void checkLocalDateTimeEncryption() {

        LocalDateTime plaintext = LocalDateTime.of(2020, Month.FEBRUARY, 20, 2, 20, 2, 20202020);
        byte[] encrypted = this.crypto.encryptLocalDateTime(plaintext);
        LocalDateTime decrypted = this.crypto.decryptLocalDateTime(encrypted);
        assertThat(decrypted, equalTo(plaintext));
    }


    @Test(expected = NullPointerException.class)
    public void checkEncryptDateWithNullParam() {

        this.crypto.encryptDate(null);
    }


    @Test(expected = NullPointerException.class)
    public void checkDecryptDateWithNullParam() {

        this.crypto.decryptDate(null);
    }


    @Test
    public void checkDateEncryption() {

        LocalDateTime dateTime = LocalDateTime.of(2020, Month.FEBRUARY, 20, 2, 20, 2, 20202020);
        Date plaintext = Date.from(dateTime.toInstant(ZoneOffset.UTC));
        byte[] encrypted = this.crypto.encryptDate(plaintext);
        Date decrypted = this.crypto.decryptDate(encrypted);
        assertThat(decrypted, equalTo(plaintext));
    }
}
