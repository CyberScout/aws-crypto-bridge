package com.cyberscout.awscrypto.core.aws;


import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CryptoAlgorithm;
import com.amazonaws.encryptionsdk.exception.BadCiphertextException;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class AwsCryptoFacadeTest {

    private AwsCrypto mockDelegate;


    @Before
    public void createObjects() throws Exception {

        this.mockDelegate = mock(AwsCrypto.class);
    }


    @Test
    public void checkDataEncryption() {

        byte[] plaintext = new byte[2000];
        new Random(System.currentTimeMillis()).nextBytes(plaintext);
    }


    @Test
    public void checkValidateContextWithCorrectSuperSet() {

        Map<String, String> context = new HashMap<>();
        context.put("First", "Value");
        context.put("2nd", "Context Value");
        AwsCryptoFacade<MockMasterKey> facade = new AwsCryptoFacade<>(mockDelegate,
                                                                      new MockMasterKey(),
                                                                      context,
                                                                      StandardCharsets.UTF_8);
        Map<String, String> decryptedContext = new HashMap<>();
        decryptedContext.put("First", "Value");
        decryptedContext.put("2nd", "Context Value");
        decryptedContext.put("AWS Stuff", "Some value set by AWS ESDK");
        facade.validateContext(decryptedContext);
    }


    @Test(expected = BadCiphertextException.class)
    public void checkValidateContextWithIncorrectSuperSet() {

        Map<String, String> context = new HashMap<>();
        context.put("First", "Value");
        context.put("2nd", "Context Value");
        AwsCryptoFacade<MockMasterKey> facade = new AwsCryptoFacade<>(mockDelegate,
                                                                      new MockMasterKey(),
                                                                      context,
                                                                      StandardCharsets.UTF_8);
        Map<String, String> decryptedContext = new HashMap<>();
        decryptedContext.put("First", "Different Value");
        decryptedContext.put("2nd", "Context Value");
        decryptedContext.put("AWS Stuff", "Some value set by AWS ESDK");
        facade.validateContext(decryptedContext);
    }


    @Test(expected = BadCiphertextException.class)
    public void checkValidateContextWithTooManyValues() {

        Map<String, String> context = new HashMap<>();
        context.put("First", "Value");
        context.put("2nd", "Context Value");
        AwsCryptoFacade<MockMasterKey> facade = new AwsCryptoFacade<>(mockDelegate,
                                                                      new MockMasterKey(),
                                                                      context,
                                                                      StandardCharsets.UTF_8);
        Map<String, String> decryptedContext = new HashMap<>();
        decryptedContext.put("First", "Value");
        decryptedContext.put("AWS Stuff", "Some value set by AWS ESDK");
        facade.validateContext(decryptedContext);
    }


    @Test
    public void checkPureDelegatedMethods() {

        MockMasterKey masterKey = new MockMasterKey();
        Map<String, String> context = new HashMap<>();
        InputStream is = mock(InputStream.class);
        OutputStream os = mock(OutputStream.class);
        AwsCryptoFacade<MockMasterKey> facade = new AwsCryptoFacade<>(mockDelegate,
                                                                      masterKey,
                                                                      context,
                                                                      StandardCharsets.UTF_8);

        facade.createEncryptingStream(is);
        verify(mockDelegate).createEncryptingStream(masterKey, is, context);

        facade.createDecryptingStream(is);
        verify(mockDelegate).createDecryptingStream(masterKey, is);

        facade.createEncryptingStream(os);
        verify(mockDelegate).createEncryptingStream(masterKey, os, context);

        facade.createDecryptingStream(os);
        verify(mockDelegate).createDecryptingStream(masterKey, os);

        facade.estimateCiphertextSize(42);
        verify(mockDelegate).estimateCiphertextSize(masterKey, 42, context);

        facade.getEncryptionAlgorithm();
        verify(mockDelegate).getEncryptionAlgorithm();

        facade.setEncryptionAlgorithm(CryptoAlgorithm.ALG_AES_256_GCM_IV12_TAG16_HKDF_SHA384_ECDSA_P384);
        verify(mockDelegate).setEncryptionAlgorithm(CryptoAlgorithm.ALG_AES_256_GCM_IV12_TAG16_HKDF_SHA384_ECDSA_P384);

        facade.getEncryptionFrameSize();
        verify(mockDelegate).getEncryptionFrameSize();

        facade.setEncryptionFrameSize(42);
        verify(mockDelegate).setEncryptionFrameSize(42);
    }


}
