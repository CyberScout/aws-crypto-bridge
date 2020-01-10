package com.cyberscout.awscrypto.core.aws;


import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.exception.BadCiphertextException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;


public class AwsCryptoFacadeBasicTest {

    private AwsCrypto mockDelegate;


    @Before
    public void createObjects() {

        this.mockDelegate = mock(AwsCrypto.class);
    }


    @Test
    public void checkValidateContextWithCorrectSuperSet() {

        Map<String, String> context = new HashMap<>();
        context.put("First", "Value");
        context.put("2nd", "Context Value");
        AwsCryptoFacade<MockMasterKey> facade = new AwsCryptoFacade<>(mockDelegate, new MockMasterKey(), context);
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
        AwsCryptoFacade<MockMasterKey> facade = new AwsCryptoFacade<>(mockDelegate, new MockMasterKey(), context);
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
        AwsCryptoFacade<MockMasterKey> facade = new AwsCryptoFacade<>(mockDelegate, new MockMasterKey(), context);
        Map<String, String> decryptedContext = new HashMap<>();
        decryptedContext.put("First", "Value");
        decryptedContext.put("AWS Stuff", "Some value set by AWS ESDK");
        facade.validateContext(decryptedContext);
    }
}
