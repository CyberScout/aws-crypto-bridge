package com.cyberscout.awscrypto.core.aws;


import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CryptoAlgorithm;
import com.amazonaws.encryptionsdk.CryptoInputStream;
import com.amazonaws.encryptionsdk.CryptoOutputStream;
import com.amazonaws.encryptionsdk.CryptoResult;
import com.amazonaws.encryptionsdk.MasterKey;
import com.amazonaws.encryptionsdk.MasterKeyProvider;
import com.amazonaws.encryptionsdk.exception.BadCiphertextException;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Provides a facade over an {@link AwsCrypto} object. This facade simplifies performing cryptographic operations
 * with an {@code AwsCrypto} by consolidating the common parameters (such as encryption context) that would be needed
 * to perform two-way encryption. It also performs the additional validation required when decrypting a value using
 * an encryption context.
 *
 * @param <K> The {@code MasterKey} implementation that will be used by this facade
 */
@SuppressWarnings({ "unused", "UnusedReturnValue" })
public class AwsCryptoFacade<K extends MasterKey<K>> {

    private final AwsCrypto delegate;
    private final MasterKeyProvider<K> mkp;
    private final Map<String, String> ctx;


    /**
     * Constructor is package-private to prevent direct instantiation. Use the
     * {@link #forMasterKeyProvider(MasterKeyProvider) builder method} instead.
     *
     * @param delegate The {@code AwsCrypto} instance to delegate to
     * @param mkp The {@code MasterKeyProvider} that will be used by the delegate for cryptographic operations
     * @param ctx The encryption context to use for encryption operations, and to validate decryption operations
     *         with. Can be empty, but must not be {@code null}.
     */
    AwsCryptoFacade(AwsCrypto delegate, MasterKeyProvider<K> mkp, Map<String, String> ctx) {

        this.delegate = delegate;
        this.mkp = mkp;
        this.ctx = new ConcurrentHashMap<>(ctx);
    }


    /**
     * Initializes a {@link Builder} to construct instances of this facade.
     *
     * @param mkp The {@code MasterKeyProvider} that will be used by the delegate for cryptographic operations
     * @param <K> The {@code MasterKey} implementation that this facade will use
     * @return A builder for constructing an instance of this facade
     */
    public static <K extends MasterKey<K>> Builder<K> forMasterKeyProvider(MasterKeyProvider<K> mkp) {

        return new Builder<>(mkp);
    }


    /**
     * Delegates encryption of the provided plaintext data to the
     * {@link AwsCrypto#encryptData(MasterKeyProvider, byte[], Map) encryptData} method, and returns the resulting
     * raw bytes.
     *
     * @param plaintext The raw plaintext data to encrypt
     * @return The raw ciphertext data
     * @see AwsCrypto#encryptData(MasterKeyProvider, byte[], Map)
     */
    public byte[] encryptData(byte[] plaintext) {

        CryptoResult<byte[], K> result = this.delegate.encryptData(this.mkp, plaintext, this.ctx);
        return result.getResult();
    }


    /**
     * Delegates decryption of the provided ciphertext data to the
     * {@link AwsCrypto#decryptData(MasterKeyProvider, byte[]) decryptData} method. It then validates the encryption
     * context before returning the raw plaintext data.
     *
     * @param ciphertext The raw ciphertext data to decrypt
     * @return The raw plaintext data
     * @see AwsCrypto#decryptData(MasterKeyProvider, byte[])
     */
    public byte[] decryptData(byte[] ciphertext) {

        CryptoResult<byte[], K> result = this.delegate.decryptData(this.mkp, ciphertext);
        this.validateContext(result.getEncryptionContext());
        return result.getResult();
    }


    /**
     * Delegates creation of an encrypting {@link InputStream} to the
     * {@link AwsCrypto#createEncryptingStream(MasterKeyProvider, InputStream, Map) createEncryptingStream} method.
     *
     * @param is The {@code InputStream} to wrap
     * @return A {@code CryptoInputStream} that will encrypt data read from the underlying {@code InputStream}
     * @see AwsCrypto#createEncryptingStream(MasterKeyProvider, InputStream, Map)
     */
    public CryptoInputStream<K> encryptingStream(InputStream is) {

        return this.delegate.createEncryptingStream(this.mkp, is, this.ctx);
    }


    /**
     * <p>
     * Delegates creation of a decrypting {@link InputStream} to the
     * {@link AwsCrypto#createDecryptingStream(MasterKeyProvider, InputStream) createDecryptingStream} method.
     * </p>
     * <p>
     * Note that the resulting stream does not automatically validate the encryption context. This must be done manually
     * by the client.
     * </p>
     *
     * @param is The {@code InputStream} to wrap
     * @return A {@code CryptInputStream} that will decrypt data read from the underlying {@code InputStream}.
     * @see AwsCrypto#createDecryptingStream(MasterKeyProvider, InputStream)
     * @see #validateContext(Map)
     */
    public CryptoInputStream<K> decryptingStream(InputStream is) {

        return this.delegate.createDecryptingStream(this.mkp, is);
    }


    /**
     * Delegates creation of an encrypting {@link OutputStream} to the
     * {@link AwsCrypto#createEncryptingStream(MasterKeyProvider, OutputStream, Map) createEncryptingStream} method.
     *
     * @param os The {@code OutputStream} to wrap
     * @return A {@code CryptoOutputStream} that will encrypt data and write it to the underlying {@code OutputStream}.
     * @see AwsCrypto#createEncryptingStream(MasterKeyProvider, OutputStream, Map)
     */
    public CryptoOutputStream<K> encryptingStream(OutputStream os) {

        return this.delegate.createEncryptingStream(this.mkp, os, this.ctx);
    }


    /**
     * <p>
     * Delegates creation of a decrypting {@link OutputStream} to the
     * {@link AwsCrypto#createDecryptingStream(MasterKeyProvider, OutputStream) createDecryptingStream} method.
     * </p>
     * <p>
     * Note that the resulting stream does not automatically validate the encryption context. This must be done manually
     * by the client.
     * </p>
     *
     * @param os The {@code OutputStream} to wrap
     * @return A {@code CryptoOutputStream} that will decrypt data and write it to the underlying {@code OutputStream}.
     * @see AwsCrypto#createDecryptingStream(MasterKeyProvider, OutputStream)
     * @see #validateContext(Map)
     */
    public CryptoOutputStream<K> decryptingStream(OutputStream os) {

        return this.delegate.createDecryptingStream(this.mkp, os);
    }


    /**
     * Delegates to the {@link AwsCrypto#estimateCiphertextSize(MasterKeyProvider, int, Map) estimateCiphertextSize}
     * method.
     *
     * @param plaintextSize The size of the plaintext that will be encrypted
     * @return The estimated size of the ciphertext
     * @see AwsCrypto#estimateCiphertextSize(MasterKeyProvider, int, Map)
     */
    public long estimateCiphertextSize(int plaintextSize) {

        return this.delegate.estimateCiphertextSize(this.mkp, plaintextSize, this.ctx);
    }


    /**
     * <p>
     * Delegates to the {@link AwsCrypto#getEncryptionAlgorithm() getEncryptionAlgorithm} method.
     * </p>
     * <p>
     * Note that this facade does not expose a means for setting the algorithm. To facilitate predictable, two-way
     * encryption operations, this object is immutable, and hides the mutability of the underlying delegate. Setting
     * the algorithm must be done at creation time, via the {@link Builder#withAlgorithm(CryptoAlgorithm) builder}.
     * </p>
     *
     * @return The algorithm that is being employed for cryptographic operations
     * @see AwsCrypto#getEncryptionAlgorithm()
     */
    public CryptoAlgorithm getEncryptionAlgorithm() {

        return this.delegate.getEncryptionAlgorithm();
    }


    /**
     * <p>
     * Delegates to the {@link AwsCrypto#getEncryptionFrameSize() getEncryptionFrameSize} method.
     * </p>
     * <p>
     * Note that this facade does not expose a means for setting the frame size. To facilitate predictable, two-way
     * encryption operations, this object is immutable, and hides the mutability of the underlying delegate. Setting
     * the frame size must be done at creation time, via the {@link Builder#withFrameSize(int) builder}.
     * </p>
     *
     * @return The encryption frame size that is being employed for cryptographic operations
     * @see AwsCrypto#getEncryptionFrameSize()
     */
    public int getEncryptionFrameSize() {

        return this.delegate.getEncryptionFrameSize();
    }


    /**
     * Utility method for validating the encryption context of a decryption operation against the context expected
     * by this facade.
     *
     * @param resultContext The encryption context from a decryption operation
     * @throws BadCiphertextException If the result context does not match the expected context
     */
    public void validateContext(Map<String, String> resultContext) {

        if (!resultContext.entrySet().containsAll(this.ctx.entrySet())) {
            throw new BadCiphertextException("Invalid encryption context");
        }
    }


    /**
     * Builder class for creating {@link AwsCryptoFacade} instances.
     *
     * @param <K> The {@code MasterKey} implementation that will be used by the facade created by this builder
     */
    public static class Builder<K extends MasterKey<K>> {

        private final MasterKeyProvider<K> mkp;
        private final Map<String, String> context = new ConcurrentHashMap<>();
        private final AwsCrypto delegate = new AwsCrypto();


        private Builder(MasterKeyProvider<K> mkp) {

            this.mkp = mkp;
        }


        /**
         * Adds all of the given encryption context values to the resulting facade. This method does not clear any
         * previously set context values, but will override any that exist with the same key.
         *
         * @param context The encryption context to set
         * @return This builder object
         */
        public Builder<K> withContext(Map<String, String> context) {

            this.context.putAll(context);
            return this;
        }


        /**
         * Adds a new key/value pair to the encryption context for the resulting facade. This method will override
         * any existing value with the same key.
         *
         * @param key The encryption context key
         * @param value The encryption context value
         * @return This builder object
         */
        public Builder<K> addContext(String key, String value) {

            this.context.put(key, value);
            return this;
        }


        /**
         * Initializes the encryption algorithm that the resulting facade will use.
         *
         * @param algorithm The {@code CryptoAlgorithm} to use
         * @return This builder object
         */
        public Builder<K> withAlgorithm(CryptoAlgorithm algorithm) {

            this.delegate.setEncryptionAlgorithm(algorithm);
            return this;
        }


        /**
         * Initializes the encryption frame size that the resulting facade will use.
         *
         * @param size The frame size to use
         * @return This builder object
         */
        public Builder<K> withFrameSize(int size) {

            this.delegate.setEncryptionFrameSize(size);
            return this;
        }


        /**
         * Constructs a new {@code AwsCryptoFacade} object, based on the values initialized via this builder.
         *
         * @return The resulting facade
         */
        public AwsCryptoFacade<K> build() {

            return new AwsCryptoFacade<>(this.delegate, this.mkp, this.context);
        }
    }
}
