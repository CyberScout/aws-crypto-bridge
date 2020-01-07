package com.cyberscout.awscrypto.hibernate;


import org.hibernate.HibernateException;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;


/**
 * Converts a persistent value between a plaintext property value and an encrypted, base64-encoded string in the
 * database.
 */
public class EncryptedStringUserType implements UserType, ParameterizedType {

    @Override
    public int[] sqlTypes() {

        return new int[] { Types.VARCHAR, Types.LONGVARCHAR, Types.NVARCHAR };
    }


    @Override
    public Class returnedClass() {

        return String.class;
    }


    @Override
    public boolean equals(Object x, Object y) throws HibernateException {

        return Objects.equals(x, y);
    }


    @Override
    public int hashCode(Object x) throws HibernateException {

        return Objects.hashCode(x);
    }


    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {

        // Get the encrypted value from the result set
        // Decrypt it
        //   Lookup the decryption service using a static registry
        // Return the plaintext value
        return null;
    }


    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {

    }


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


    @Override
    public void setParameterValues(Properties parameters) {

    }
}
