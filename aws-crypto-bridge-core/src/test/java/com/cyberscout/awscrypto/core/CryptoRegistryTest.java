package com.cyberscout.awscrypto.core;


import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;


public class CryptoRegistryTest {

    private CryptoRegistry registry = CryptoRegistry.instance();


    @Test
    public void checkTypeSafety() {

        StringCrypto stringCrypto = proxy(StringCrypto.class);
        ByteCrypto byteCrypto = proxy(ByteCrypto.class);

        registry.register("strings", stringCrypto);
        registry.register("bytes", byteCrypto);

        StringCrypto strings = registry.lookup("strings", StringCrypto.class);
        assertSame(stringCrypto, strings);
        ByteCrypto bytes = registry.lookup("bytes", ByteCrypto.class);
        assertSame(byteCrypto, bytes);

        try {
            bytes = registry.lookup("strings", ByteCrypto.class);
            fail();
        }
        catch (CryptoBridgeConfigException expected) {
        }
        try {
            CryptoOperations<String> res = registry.lookup("bytes", StringCrypto.class);
            fail();
        }
        catch (CryptoBridgeConfigException expected) {
        }
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
