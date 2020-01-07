package com.cyberscout.awscrypto.core;


import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.MasterKeyProvider;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class AwsCryptoAdaptor implements Encryptor, Decryptor {

    private final AwsCrypto delegate;
    private final MasterKeyProvider<?> masterKeyProvider;
    private final Map<String, String> context;
    private final Charset charset;


    AwsCryptoAdaptor(AwsCrypto delegate,
                     MasterKeyProvider<?> masterKeyProvider,
                     Map<String, String> context,
                     Charset charset) {

        this.delegate = delegate;
        this.masterKeyProvider = masterKeyProvider;
        this.context = context;
        this.charset = charset;
    }


    public static Builder delegateTo(AwsCrypto delegate, MasterKeyProvider<?> masterKeyProvider) {

        return new Builder(delegate, masterKeyProvider);
    }


    @Override
    public byte[] encryptAsBytes(String plaintext) {

        return new byte[0];
    }


    @Override
    public String encryptAsBase64(String plaintext) {

        return null;
    }


    private byte[] convertStringToBytes(String str) {

        return str.getBytes(this.charset);
    }


    public static final class Builder {

        private AwsCrypto delegate;
        private MasterKeyProvider<?> mkp;
        private Map<String, String> ctx = new HashMap<>();
        private Charset charset = StandardCharsets.UTF_8;


        Builder(AwsCrypto delegate, MasterKeyProvider<?> mkp) {

            Objects.requireNonNull(delegate, "delegate");
            Objects.requireNonNull(mkp, "masterKeyProvider");
            this.delegate = delegate;
            this.mkp = mkp;
        }


        public Builder withContext(Map<String, String> context) {

            this.ctx.putAll(context);
            return this;
        }


        public Builder addContextValue(String key, String value) {

            this.ctx.put(key, value);
            return this;
        }


        public Builder withCharset(Charset charset) {

            this.charset = charset;
            return this;
        }


        public Builder withCharset(String charsetName) {

            this.charset = Charset.forName(charsetName);
            return this;
        }


        public AwsCryptoAdaptor build() {

            return new AwsCryptoAdaptor(this.delegate, this.mkp, this.ctx, this.charset);
        }
    }
}
