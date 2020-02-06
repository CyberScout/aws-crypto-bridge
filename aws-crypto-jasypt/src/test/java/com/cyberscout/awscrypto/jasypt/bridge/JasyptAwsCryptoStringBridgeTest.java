package com.cyberscout.awscrypto.jasypt.bridge;


import com.amazonaws.encryptionsdk.exception.BadCiphertextException;
import com.cyberscout.awscrypto.core.Base64StringCrypto;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class JasyptAwsCryptoStringBridgeTest {

    @Mock
    private Base64StringCrypto mockDelegate;
    @Mock
    private PBEStringEncryptor mockLegacy;

    private JasyptAwsCryptoStringBridge testEncryptor;


    @Before
    public void createTestObjects() {

        this.testEncryptor = new JasyptAwsCryptoStringBridge(this.mockDelegate, this.mockLegacy);
    }


    @Test
    public void checkEncryptionDelegates() {

        String message = "Sweet mother of monkey milk!";
        this.testEncryptor.encrypt(message);
        verify(this.mockDelegate).encryptString(message);
    }


    @Test
    public void checkDecryptionDelegates() {

        String encryptedMessage = "I can fix it!";
        this.testEncryptor.decrypt(encryptedMessage);
        verify(this.mockDelegate).decryptString(encryptedMessage);
    }


    @Test
    public void checkDecryptionFallsBackToLegacy() {

        String encryptedMessage = "I'm gonna wreck it!";
        when(this.mockDelegate.decryptString(encryptedMessage)).thenThrow(BadCiphertextException.class);
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
