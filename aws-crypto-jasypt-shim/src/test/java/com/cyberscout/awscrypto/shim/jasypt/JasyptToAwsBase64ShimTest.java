package com.cyberscout.awscrypto.shim.jasypt;


import com.cyberscout.awscrypto.core.Base64StringCrypto;
import org.jasypt.encryption.StringEncryptor;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class JasyptToAwsBase64ShimTest {

    @Mock
    Base64StringCrypto cryptoDelegate;
    @Mock
    StringEncryptor legacyDecryptor;
}
