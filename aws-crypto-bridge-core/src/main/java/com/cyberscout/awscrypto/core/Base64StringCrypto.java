package com.cyberscout.awscrypto.core;


import java.util.Base64;


/**
 * {@link CryptoOperations} sub-interface that converts encrypted data to Base64-encoded strings, and vice versa.
 */
public interface Base64StringCrypto extends CryptoOperations<String> {

    Base64.Decoder BASE64_DECODER = Base64.getMimeDecoder();
    Base64.Encoder BASE64_ENCODER = Base64.getMimeEncoder();
}
