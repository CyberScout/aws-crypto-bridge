package com.cyberscout.awscrypto.hibernate;


import com.cyberscout.awscrypto.core.Base64StringCrypto;
import com.cyberscout.awscrypto.core.CryptoRegistry;
import com.cyberscout.awscrypto.core.exceptions.CryptoBridgeConfigException;
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

import static com.cyberscout.awscrypto.hibernate.Parameters.CRYPTO_NAME;


public abstract class BaseImmutableEncryptedStringType implements UserType, ParameterizedType {

    private static final int[] SQL_TYPES = new int[] { Types.VARCHAR };

    private String cryptoName;
    private boolean initialized = false;
    private final Object initializationLock = new Object();


    @Override
    public int[] sqlTypes() {

        return SQL_TYPES;
    }


    @Override
    public Class<String> returnedClass() {

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

        this.initialize();
        String colName = names[0];
        String ciphertext = rs.getString(colName);
        if (ciphertext == null) {
            return null;
        }
        return this.decryptValue(ciphertext);
    }


    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {

        this.initialize();
        if (value == null) {
            st.setNull(index, SQL_TYPES[0]);
        }
        else {
            String ciphertext = this.encryptValue(value);
            st.setString(index, ciphertext);
        }
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

        if (parameters.containsKey(CRYPTO_NAME)) {
            this.cryptoName = parameters.getProperty(CRYPTO_NAME);
        }
    }


    protected final Base64StringCrypto getCrypto() {

        return CryptoRegistry.instance().lookup(this.cryptoName, Base64StringCrypto.class);
    }


    protected final void initialize() throws CryptoBridgeConfigException {

        if (this.initialized) {
            return;
        }
        synchronized (this.initializationLock) {
            if (this.initialized) {
                return;
            }
            if (this.cryptoName == null || this.cryptoName.trim().equals("")) {
                this.cryptoName = CryptoRegistry.DEFAULT_NAME;
            }
            // Future enhancement:
            // Accept properties to create and register the AwsCryptoFacade and Base64Adaptor directly
            // Attempt to fetch the CryptoOperations instance, which will throw an exception if it isn't configured
            // correctly.
            this.getCrypto();
            this.initialized = true;
        }
    }


    protected abstract Object decryptValue(String encryptedVal);


    protected abstract String encryptValue(Object plainVal);
}
