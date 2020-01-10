package com.cyberscout.awscrypto.core.aws;


import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CryptoAlgorithm;
import com.amazonaws.encryptionsdk.CryptoInputStream;
import com.amazonaws.encryptionsdk.CryptoOutputStream;
import com.amazonaws.encryptionsdk.CryptoResult;
import com.amazonaws.encryptionsdk.MasterKey;
import com.amazonaws.encryptionsdk.MasterKeyProvider;
import com.amazonaws.encryptionsdk.exception.BadCiphertextException;
import lombok.Getter;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class AwsCryptoFacade<K extends MasterKey<K>> {

    private final AwsCrypto delegate;
    private final MasterKeyProvider<K> mkp;
    private final Map<String, String> ctx;
    @Getter
    private final Charset charset;


    AwsCryptoFacade(AwsCrypto delegate, MasterKeyProvider<K> mkp, Map<String, String> ctx, Charset charset) {

        this.delegate = delegate;
        this.mkp = mkp;
        this.ctx = new ConcurrentHashMap<>(ctx);
        this.charset = charset;
    }


    public static <K extends MasterKey<K>> Builder<K> forMasterKeyProvider(MasterKeyProvider<K> mkp) {

        return new Builder<>(mkp);
    }


    public byte[] encryptData(byte[] plaintext) {

        CryptoResult<byte[], K> result = this.delegate.encryptData(this.mkp, plaintext, this.ctx);
        return result.getResult();
    }


    public byte[] decryptData(byte[] ciphertext) {

        CryptoResult<byte[], K> result = this.delegate.decryptData(this.mkp, ciphertext);
        this.validateContext(result.getEncryptionContext());
        return result.getResult();
    }


    public CryptoInputStream<K> createEncryptingStream(InputStream is) {

        return this.delegate.createEncryptingStream(this.mkp, is, this.ctx);
    }


    public CryptoInputStream<K> createDecryptingStream(InputStream is) {

        return this.delegate.createDecryptingStream(this.mkp, is);
    }


    public CryptoOutputStream<K> createEncryptingStream(OutputStream os) {

        return this.delegate.createEncryptingStream(this.mkp, os, this.ctx);
    }


    public CryptoOutputStream<K> createDecryptingStream(OutputStream os) {

        return this.delegate.createDecryptingStream(this.mkp, os);
    }


    public long estimateCiphertextSize(int plaintextSize) {

        return this.delegate.estimateCiphertextSize(this.mkp, plaintextSize, this.ctx);
    }


    public CryptoAlgorithm getEncryptionAlgorithm() {

        return this.delegate.getEncryptionAlgorithm();
    }


    public void setEncryptionAlgorithm(CryptoAlgorithm alg) {

        this.delegate.setEncryptionAlgorithm(alg);
    }


    public int getEncryptionFrameSize() {

        return this.delegate.getEncryptionFrameSize();
    }


    public void setEncryptionFrameSize(int frameSize) {

        this.delegate.setEncryptionFrameSize(frameSize);
    }


    public void validateContext(Map<String, String> resultContext) {

        if (!resultContext.entrySet().containsAll(this.ctx.entrySet())) {
            throw new BadCiphertextException("Invalid encryption context");
        }
    }


    private static class Builder<K extends MasterKey<K>> {

        private final MasterKeyProvider<K> mkp;
        private final Map<String, String> context = new ConcurrentHashMap<>();
        private Charset charset = StandardCharsets.UTF_8;


        private Builder(MasterKeyProvider<K> mkp) {

            this.mkp = mkp;
        }


        public Builder<K> withContext(Map<String, String> context) {

            this.context.putAll(context);
            return this;
        }


        public Builder<K> addContext(String key, String value) {

            this.context.put(key, value);
            return this;
        }


        public Builder<K> withCharset(Charset charset) {

            this.charset = charset;
            return this;
        }


        public Builder<K> withCharset(String charset) {

            this.charset = Charset.forName(charset);
            return this;
        }


        public AwsCryptoFacade<K> build() {

            return new AwsCryptoFacade<>(new AwsCrypto(), this.mkp, this.context, this.charset);
        }
    }
}
