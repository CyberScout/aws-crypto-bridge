package com.cyberscout.awscrypto.hibernate;


import com.cyberscout.awscrypto.core.Base64StringCrypto;
import com.cyberscout.awscrypto.core.CryptoRegistry;
import com.cyberscout.awscrypto.core.exceptions.CryptoBridgeConfigException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Properties;

import static com.cyberscout.awscrypto.hibernate.Parameters.CRYPTO_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class BaseImmutableEncryptedStringTypeTest {

    private static final String cryptoName = "testCrypto";
    private BaseImmutableEncryptedStringType testType = new TestType();
    private CryptoRegistry registry = CryptoRegistry.instance();
    @Mock
    private Base64StringCrypto mockCrypto;


    @After
    public void cleanupRegistry() {

        registry.clear();
    }


    @Test
    public void checkInitializationWithName() {

        registry.register(cryptoName, mockCrypto);
        Properties props = new Properties();
        props.setProperty(CRYPTO_NAME, cryptoName);
        testType.setParameterValues(props);
        testType.initialize();
        Base64StringCrypto resultCrypto = testType.getCrypto();
        assertThat(resultCrypto, is(mockCrypto));
    }


    @Test
    public void checkInitializationWithoutName() {

        registry.registerDefault(mockCrypto);
        testType.initialize();
        Base64StringCrypto resultCrypto = testType.getCrypto();
        assertThat(resultCrypto, is(mockCrypto));
    }

    @Test
    public void checkAllWhitespaceNameFallsBackToDefault() {

        registry.registerDefault(mockCrypto);
        Properties props = new Properties();
        props.setProperty(CRYPTO_NAME, " \t\n  \t");
        testType.setParameterValues(props);
        testType.initialize();
        Base64StringCrypto resultCrypto = testType.getCrypto();
        assertThat(resultCrypto, is(mockCrypto));
    }


    @Test(expected = CryptoBridgeConfigException.class)
    public void checkInitializationWithUnregisteredCrypto() {

        registry.register("blah blah", mockCrypto);
        Properties props = new Properties();
        props.setProperty(CRYPTO_NAME, cryptoName);
        testType.setParameterValues(props);
        testType.initialize();
    }


    private static class TestType extends BaseImmutableEncryptedStringType {


        @Override
        protected Object decryptValue(String encryptedVal) {

            return null;
        }


        @Override
        protected String encryptValue(Object plainVal) {

            return null;
        }
    }
}
