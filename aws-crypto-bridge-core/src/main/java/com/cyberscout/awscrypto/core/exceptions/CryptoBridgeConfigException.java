package com.cyberscout.awscrypto.core.exceptions;


/**
 * Exception thrown when the Crypto Bridge is incorrectly configured.
 */
public class CryptoBridgeConfigException extends RuntimeException {

    public CryptoBridgeConfigException(String message) {

        super(message);
    }
}
