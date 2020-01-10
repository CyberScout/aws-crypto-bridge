package com.cyberscout.awscrypto.core;


import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.junit.Assert.assertSame;


public class CryptoRegistryTest {

    private CryptoRegistry registry = CryptoRegistry.instance();


    @Test
    public void checkRegisterAndLookup() {

        StringCrypto stringCrypto = proxy(StringCrypto.class);
        ByteCrypto byteCrypto = proxy(ByteCrypto.class);

        registry.register("strings", stringCrypto);
        registry.register("bytes", byteCrypto);

        StringCrypto strings = registry.lookup("strings", StringCrypto.class);
        assertSame(stringCrypto, strings);
        ByteCrypto bytes = registry.lookup("bytes", ByteCrypto.class);
        assertSame(byteCrypto, bytes);
    }


    @Test(expected = CryptoBridgeConfigException.class)
    public void checkLookupThrowsExceptionForWrongType() {

        StringCrypto stringCrypto = proxy(StringCrypto.class);
        registry.register("test", stringCrypto);
        registry.lookup("name", ByteCrypto.class);
    }


    @Test(expected = CryptoBridgeConfigException.class)
    public void checkLookupThrowsExceptionForWrongName() {

        ByteCrypto byteCrypto = proxy(ByteCrypto.class);
        registry.register("test", byteCrypto);
        registry.lookup("xyz", ByteCrypto.class);
    }


    @Test
    public void checkRegisterAndLookupDefault() {

        ByteCrypto byteCrypto = proxy(ByteCrypto.class);
        registry.registerDefault(byteCrypto);
        ByteCrypto found = registry.lookupDefault(ByteCrypto.class);
        assertSame(byteCrypto, found);
    }


    private <T> T proxy(Class<T> clazz) {

        InvocationHandler handler = (proxy, method, args) -> null;
        Object proxyObj = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, handler);
        return clazz.cast(proxyObj);
    }


    private interface StringCrypto extends CryptoOperations<String> {

    }


    private interface ByteCrypto extends CryptoOperations<byte[]> {

    }
}
