package com.cyberscout.awscrypto.core.aws;


import com.cyberscout.awscrypto.core.CryptoOperations;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;


@RequiredArgsConstructor(access = PROTECTED)
public abstract class BaseAwsCryptoOperations<T> implements CryptoOperations<T> {

    private static final DateTimeFormatter LOCAL_DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public static final byte BYTE_TRUE = 0x1;
    public static final byte BYTE_FALSE = 0x0;

    @Getter(PROTECTED)
    private final AwsCryptoFacade<?> crypto;
    @Getter(PROTECTED)
    private final Charset charset;


    protected BaseAwsCryptoOperations(AwsCryptoFacade<?> cryptoFacade) {

        this(cryptoFacade, StandardCharsets.UTF_8);
    }


    @Override
    public T encryptLong(long plainVal) {

        byte[] plainBytes = ByteBuffer.allocate(Long.BYTES).putLong(plainVal).array();
        return this.encryptByteArray(plainBytes);
    }


    @Override
    public long decryptLong(T encryptedVal) {

        byte[] plainBytes = this.decryptByteArray(encryptedVal);
        return ByteBuffer.wrap(plainBytes).getLong();
    }


    @Override
    public T encryptInteger(int plainVal) {

        byte[] plainBytes = ByteBuffer.allocate(Integer.BYTES).putInt(plainVal).array();
        return this.encryptByteArray(plainBytes);
    }


    @Override
    public int decryptInteger(T encryptedVal) {

        byte[] plainBytes = this.decryptByteArray(encryptedVal);
        return ByteBuffer.wrap(plainBytes).getInt();
    }


    @Override
    public T encryptBoolean(boolean plainVal) {

        byte[] plainBytes = new byte[] {
                plainVal ? BYTE_TRUE : BYTE_FALSE
        };
        return this.encryptByteArray(plainBytes);
    }


    @Override
    public boolean decryptBoolean(T encryptedVal) {

        byte[] plainBytes = this.decryptByteArray(encryptedVal);
        return plainBytes[0] == BYTE_TRUE;
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


    @Override
    public T encryptLocalDateTime(LocalDateTime plainVal) {

        Objects.requireNonNull(plainVal, "plainVal");
        String formatted = plainVal.format(LOCAL_DATE_TIME_FORMAT);
        return this.encryptString(formatted);
    }


    @Override
    public LocalDateTime decryptLocalDateTime(T encryptedVal) {

        Objects.requireNonNull(encryptedVal, "encryptedVal");
        String decrypted = this.decryptString(encryptedVal);
        return LocalDateTime.parse(decrypted, LOCAL_DATE_TIME_FORMAT);
    }


    @Override
    public T encryptDate(Date plainVal) {

        Objects.requireNonNull(plainVal, "plainVal");
        LocalDateTime converted = plainVal.toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime();
        return this.encryptLocalDateTime(converted);
    }


    @SneakyThrows
    @Override
    public Date decryptDate(T encryptedVal) {

        Objects.requireNonNull(encryptedVal, "encryptedVal");
        LocalDateTime decrypted = this.decryptLocalDateTime(encryptedVal);
        return Date.from(decrypted.atOffset(ZoneOffset.UTC).toInstant());
    }
}
