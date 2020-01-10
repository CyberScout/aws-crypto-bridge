package com.cyberscout.awscrypto.core.aws;


import com.amazonaws.encryptionsdk.CryptoAlgorithm;
import com.amazonaws.encryptionsdk.DataKey;
import com.amazonaws.encryptionsdk.EncryptedDataKey;
import com.amazonaws.encryptionsdk.MasterKey;
import com.amazonaws.encryptionsdk.exception.AwsCryptoException;
import com.amazonaws.encryptionsdk.exception.UnsupportedProviderException;

import java.util.Collection;
import java.util.Map;


class MockMasterKey extends MasterKey<MockMasterKey> {

    @Override
    public String getProviderId() {

        return null;
    }


    @Override
    public String getKeyId() {

        return null;
    }


    @Override
    public DataKey<MockMasterKey> generateDataKey(CryptoAlgorithm algorithm, Map<String, String> encryptionContext) {

        return null;
    }


    @Override
    public DataKey<MockMasterKey> encryptDataKey(CryptoAlgorithm algorithm,
                                                 Map<String, String> encryptionContext,
                                                 DataKey<?> dataKey) {

        return null;
    }


    @Override
    public DataKey<MockMasterKey> decryptDataKey(CryptoAlgorithm algorithm,
                                                 Collection<? extends EncryptedDataKey> encryptedDataKeys,
                                                 Map<String, String> encryptionContext)
            throws UnsupportedProviderException, AwsCryptoException {

        return null;
    }
}
