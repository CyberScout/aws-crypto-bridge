/**
 * This package provides implementations of Jasypt's various "encryptor" interfaces that delegate to a {@code
 * CryptoOperations} object, but will fallback to a "legacy" encryptor object for decryption operations. This allows
 * an application to gradually transition from another encryption mechanism to the AWS Encryption SDK. Since these
 * classes implement Jasypt's interfaces, they can be plugged into the existing Jasypt integrations (e.g. Hibernate
 * {@code UserTypes}).
 */
package com.cyberscout.awscrypto.jasypt.bridge;
