package com.cyberscout.awscrypto.hibernate;


import com.cyberscout.awscrypto.core.Base64StringCrypto;
import com.cyberscout.awscrypto.core.CryptoRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class StringToEncryptedStringTypeTest {


    private CryptoRegistry registry = CryptoRegistry.instance();
    private StringToEncryptedStringType testType = new StringToEncryptedStringType();
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

        final String plaintext = "Gimme Gimme Shock Treatment!";
        final String ciphertext = new StringBuilder(plaintext).reverse().toString().toLowerCase();
        when(mockCrypto.encryptString(plaintext)).thenReturn(ciphertext);
        when(mockCrypto.decryptString(ciphertext)).thenReturn(plaintext);

        String encrypted = testType.encryptValue(plaintext);
        assertThat(encrypted, is(ciphertext));
        Object decrypted = testType.decryptValue(encrypted);
        assertThat(decrypted, is(plaintext));
    }
}
