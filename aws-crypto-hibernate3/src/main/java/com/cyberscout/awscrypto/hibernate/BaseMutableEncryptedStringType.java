package com.cyberscout.awscrypto.hibernate;


import org.hibernate.HibernateException;
import org.hibernate.TypeMismatchException;

import java.io.Serializable;
import java.util.Date;


public abstract class BaseMutableEncryptedStringType extends BaseEncryptedStringType {

    @Override
    public Object deepCopy(Object value) throws HibernateException {

        if (value == null) {
            return null;
        }
        try {
            Date other = (Date) value;
            return new Date(other.getTime());
        }
        catch (ClassCastException e) {
            throw new TypeMismatchException(e);
        }
    }


    @Override
    public boolean isMutable() {

        return true;
    }


    @Override
    public Serializable disassemble(Object value) throws HibernateException {

        return (Date) deepCopy(value);
    }


    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {

        return deepCopy(cached);
    }


    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {

        return deepCopy(original);
    }
}
