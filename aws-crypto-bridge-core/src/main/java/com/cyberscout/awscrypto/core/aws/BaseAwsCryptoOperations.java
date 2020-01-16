package com.cyberscout.awscrypto.core.aws;


import com.cyberscout.awscrypto.core.CryptoOperations;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;


@RequiredArgsConstructor(access = PROTECTED)
public abstract class BaseAwsCryptoOperations<T> implements CryptoOperations<T> {

    private static final DateTimeFormatter LOCAL_DATE_FORMAT = DateTimeFormatter.BASIC_ISO_DATE;

    @Getter(PROTECTED)
    private final AwsCryptoFacade<?> crypto;
    @Getter(PROTECTED)
    private final Charset charset;


    protected BaseAwsCryptoOperations(AwsCryptoFacade<?> cryptoFacade) {

        this(cryptoFacade, StandardCharsets.UTF_8);
    }


    @Override
    public T encryptLocalDate(LocalDate plainVal) {

        Objects.requireNonNull(plainVal, "plainVal");
        String formatted = plainVal.format(LOCAL_DATE_FORMAT);
        return this.encryptString(formatted);
    }


    @Override
    public LocalDate decryptLocalDate(T encryptedVal) {

        Objects.requireNonNull(encryptedVal, "encryptedVal");
        String decrypted = this.decryptString(encryptedVal);
        return LocalDate.parse(decrypted, LOCAL_DATE_FORMAT);
    }
}