package com.cyberscout.awscrypto.core;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class CryptoRegistry {

    private static CryptoRegistry INSTANCE = new CryptoRegistry();

    private Map<String, CryptoOperations<?>> registry = new ConcurrentHashMap<>();


    private CryptoRegistry() {

    }


    public static CryptoRegistry instance() {

        return INSTANCE;
    }


    public void register(String name, CryptoOperations<?> ops) {

        this.registry.put(name, ops);
    }


    public <T extends CryptoOperations<?>> T lookup(String name, Class<T> type) {

        if (!this.registry.containsKey(name)) {
            throw new CryptoBridgeConfigException(String.format("No registered crypto operations named '%s'", name));
        }
        CryptoOperations<?> ops = this.registry.get(name);
        try {
            return type.cast(ops);
        }
        catch (ClassCastException e) {
            throw new CryptoBridgeConfigException(String.format("Crypto operations '%s' is not of type %s",
                                                                name,
                                                                type));
        }
    }
}
