package com.cyberscout.awscrypto.jasypt.bridge;


import com.amazonaws.encryptionsdk.exception.BadCiphertextException;
import com.cyberscout.awscrypto.core.ByteArrayCrypto;
import org.jasypt.encryption.pbe.PBEByteEncryptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class JasyptAwsCryptoByteBridgeTest {

    @Mock
    private ByteArrayCrypto mockDelegate;
    @Mock
    private PBEByteEncryptor mockLegacy;

    private JasyptAwsCryptoByteBridge testEncryptor;


    @Before
    public void createTestObjects() {

        this.testEncryptor = new JasyptAwsCryptoByteBridge(this.mockDelegate, this.mockLegacy);
    }


    @Test
    public void checkEncryptionDelegates() {

        byte[] message = "Sweet mother of monkey milk!".getBytes();
        this.testEncryptor.encrypt(message);
        verify(this.mockDelegate).encryptByteArray(message);
    }


    @Test
    public void checkDecryptionDelegates() {

        byte[] encryptedMessage = "I can fix it!".getBytes();
        this.testEncryptor.decrypt(encryptedMessage);
        verify(this.mockDelegate).decryptByteArray(encryptedMessage);
    }


    @Test
    public void checkDecryptionFallsBackToLegacy() {

        byte[] encryptedMessage = "I'm gonna wreck it!".getBytes();
        when(this.mockDelegate.decryptByteArray(encryptedMessage)).thenThrow(BadCiphertextException.class);
        this.testEncryptor.decrypt(encryptedMessage);
        verify(this.mockLegacy).decrypt(encryptedMessage);
    }


    @Test
    public void checkSetPasswordDelegates() {

        String password = "Glitch";
        this.testEncryptor.setPassword(password);
        verify(this.mockLegacy).setPassword(password);
    }
}
