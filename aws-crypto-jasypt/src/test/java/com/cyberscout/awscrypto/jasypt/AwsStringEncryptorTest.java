package com.cyberscout.awscrypto.jasypt;


import com.cyberscout.awscrypto.core.Base64StringCrypto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class AwsStringEncryptorTest {

    @Mock
    private Base64StringCrypto mockDelegate;

    private AwsStringEncryptor testEncryptor;


    @Before
    public void createTestObjects() {

        this.testEncryptor = new AwsStringEncryptor(this.mockDelegate);
    }


    @Test
    public void checkEncryptionDelegation() {

        String message = "Great googly moogly!";
        this.testEncryptor.encrypt(message);
        verify(this.mockDelegate).encryptString(message);
    }


    @Test
    public void checkDecryptionDelegation() {

        String encryptedMessage = "Wiggidy wiggidy wack";
        this.testEncryptor.decrypt(encryptedMessage);
        verify(this.mockDelegate).decryptString(encryptedMessage);
    }
}
