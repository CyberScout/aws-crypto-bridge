package com.cyberscout.awscrypto.core.aws;


import com.amazonaws.encryptionsdk.AwsCrypto;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


/**
 * Test harness to ensure that the {@link AwsCryptoFacade} properly delegates certain methods to the underlying
 * {@link AwsCrypto} object.
 */
public class AwsCryptoFacadeDelegationTest {

    private AwsCrypto mockDelegate;
    private MockMasterKey masterKey;
    private HashMap<String, String> context;
    private InputStream mockInputStream;
    private OutputStream mockOutputStream;
    private AwsCryptoFacade<MockMasterKey> facade;


    @Before
    public void createObjects() {

        this.mockDelegate = mock(AwsCrypto.class);
        this.masterKey = new MockMasterKey();
        this.context = new HashMap<>();
        this.mockInputStream = mock(InputStream.class);
        this.mockOutputStream = mock(OutputStream.class);
        this.facade = new AwsCryptoFacade<>(mockDelegate, masterKey, context);
    }


    @Test
    public void checkCreateEncryptingInputStream() {

        this.facade.encryptingStream(this.mockInputStream);
        verify(this.mockDelegate).createEncryptingStream(this.masterKey, this.mockInputStream, this.context);
    }


    @Test
    public void checkCreateDecryptingInputStream() {

        this.facade.decryptingStream(this.mockInputStream);
        verify(this.mockDelegate).createDecryptingStream(this.masterKey, this.mockInputStream);
    }


    @Test
    public void checkCreateEncryptingOutputStream() {

        this.facade.encryptingStream(this.mockOutputStream);
        verify(this.mockDelegate).createEncryptingStream(this.masterKey, this.mockOutputStream, this.context);
    }


    @Test
    public void checkCreateDecryptingOutputStream() {

        this.facade.decryptingStream(this.mockOutputStream);
        verify(this.mockDelegate).createDecryptingStream(this.masterKey, this.mockOutputStream);
    }


    @Test
    public void checkEstimateCiphertextSize() {

        this.facade.estimateCiphertextSize(42);
        verify(this.mockDelegate).estimateCiphertextSize(this.masterKey, 42, this.context);
    }


    @Test
    public void checkGetEncryptionAlgorithm() {

        this.facade.getEncryptionAlgorithm();
        verify(this.mockDelegate).getEncryptionAlgorithm();
    }


    @Test
    public void checkGetEncryptionFrameSize() {

        this.facade.getEncryptionFrameSize();
        verify(this.mockDelegate).getEncryptionFrameSize();
    }
}
