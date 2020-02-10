package com.cyberscout.awscrypto.jasypt;


import com.cyberscout.awscrypto.core.ByteArrayCrypto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class AwsByteEncryptorTest {

    @Mock
    private ByteArrayCrypto mockDelegate;

    private AwsByteEncryptor testEncryptor;


    @Before
    public void createTestObjects() {

        this.testEncryptor = new AwsByteEncryptor(mockDelegate);
    }


    @Test
    public void checkEncryptionDelegation() {

        byte[] message = "Great googly moogly!".getBytes();
        this.testEncryptor.encrypt(message);
        verify(this.mockDelegate).encryptByteArray(message);
    }


    @Test
    public void checkDecryptionDelegation() {

        byte[] encryptedMessage = "Wiggidy wiggidy wack".getBytes();
        this.testEncryptor.decrypt(encryptedMessage);
        verify(this.mockDelegate).decryptByteArray(encryptedMessage);
    }
}
