package com.cyberscout.awscrypto.core;


import com.amazonaws.encryptionsdk.AwsCrypto;
import org.junit.Before;

import static org.mockito.Mockito.mock;


public class AwsCryptoAdaptorTest {

    private AwsCrypto mockDelegate;


    @Before
    public void createMock() throws Exception {

        mockDelegate = mock(AwsCrypto.class);
    }
}
