package com.cyberscout.awscrypto.hibernate;


import org.hibernate.HibernateException;

import java.io.Serializable;
import java.util.Objects;


public abstract class BaseImmutableEncryptedStringType extends BaseEncryptedStringType {

    @Override
    public Object deepCopy(Object value) throws HibernateException {

        return value;
    }


    @Override
    public boolean isMutable() {

        return false;
    }


    @Override
    public Serializable disassemble(Object value) throws HibernateException {

        return value == null ? null : Objects.toString(value);
    }


    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {

        return cached;
    }


    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {

        return original;
    }
}
