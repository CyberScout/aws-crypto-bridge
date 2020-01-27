package com.cyberscout.awscrypto.core.aws.test;


import com.amazonaws.encryptionsdk.CryptoAlgorithm;
import com.amazonaws.encryptionsdk.DataKey;
import com.amazonaws.encryptionsdk.EncryptedDataKey;
import com.amazonaws.encryptionsdk.MasterKey;
import com.amazonaws.encryptionsdk.exception.AwsCryptoException;

import java.util.Collection;
import java.util.Map;


public class DummyMasterKey extends MasterKey<DummyMasterKey> {

    @Override
    public String getProviderId() {

        return null;
    }


    @Override
    public String getKeyId() {

        return null;
    }


    @Override
    public DataKey<DummyMasterKey> generateDataKey(CryptoAlgorithm algorithm, Map<String, String> encryptionContext) {

        return null;
    }


    @Override
    public DataKey<DummyMasterKey> encryptDataKey(CryptoAlgorithm algorithm,
                                                  Map<String, String> encryptionContext,
                                                  DataKey<?> dataKey) {

        return null;
    }


    @Override
    public DataKey<DummyMasterKey> decryptDataKey(CryptoAlgorithm algorithm,
                                                  Collection<? extends EncryptedDataKey> encryptedDataKeys,
                                                  Map<String, String> encryptionContext) throws AwsCryptoException {

        return null;
    }
}
