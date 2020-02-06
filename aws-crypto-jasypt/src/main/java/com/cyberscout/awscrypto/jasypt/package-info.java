/**
 * This package provides implementations of Jasypt's various "encryptor" interfaces that delegate to a {@code
 * CryptoOperations} object. These objects can then be plugged into the existing Jasypt integrations (e.g. Hibernate
 * {@code UserTypes}) to support transparent encryption and decryption of data via the AWS Encryption SDK.
 */
package com.cyberscout.awscrypto.jasypt;
