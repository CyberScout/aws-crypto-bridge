package com.cyberscout.awscrypto.hibernate;


import com.cyberscout.awscrypto.core.Base64StringCrypto;
import com.cyberscout.awscrypto.core.CryptoRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class DateToEncryptedStringTypeTest {

    private CryptoRegistry registry = CryptoRegistry.instance();
    private DateToEncryptedStringType testType = new DateToEncryptedStringType();
    @Mock
    private Base64StringCrypto mockCrypto;


    @Before
    public void initializeTestObjects() {

        registry.registerDefault(mockCrypto);
        testType.initialize();
    }


    @After
    public void cleanupRegistry() {

        registry.clear();
    }


    @Test
    public void checkCrypto() {

        final Date plaintext = Date.from(LocalDate
                                                 .of(2020, 2, 20)
                                                 .atStartOfDay()
                                                 .atZone(ZoneId.systemDefault())
                                                 .toInstant());
        final String ciphertext = Date
                .from(LocalDate.of(2112, 12, 21).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
                .toString();
        when(mockCrypto.encryptDate(plaintext)).thenReturn(ciphertext);
        when(mockCrypto.decryptDate(ciphertext)).thenReturn(plaintext);

        String encrypted = testType.encryptValue(plaintext);
        assertThat(encrypted, is(ciphertext));
        Object decrypted = testType.decryptValue(encrypted);
        assertThat(decrypted, is(plaintext));
    }
}
