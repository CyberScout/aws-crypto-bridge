package com.cyberscout.awscrypto.core;


import com.cyberscout.awscrypto.core.exceptions.CryptoBridgeConfigException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * <p>
 * Singleton class that provides a central, framework-agnostic registry for holding and retrieving
 * {@link CryptoOperations} objects. This registry facilitates creating any necessary cryptographic wrappers during
 * application start-up/bootstrap, and then retrieving them elsewhere. {@link CryptoOperations} objects do not
 * <em>need</em> to be cached here, but all higher-level integrations under the {@code aws-crypto-bridge} umbrella do
 * so.
 * </p>
 * <p>
 * This registry should be generally thread-safe. However, the expected usage pattern is that all modification
 * operations will take place during a well-defined bootstrap period, before the application is available for use.
 * Subsequent usages would then be limited to reading from the registry, which will not cause any concurrency issues.
 * </p>
 */
public final class CryptoRegistry {

    public static final String DEFAULT_NAME = "DEFAULT";
    private static final CryptoRegistry INSTANCE = new CryptoRegistry();

    private final Map<String, CryptoOperations<?>> registry = new ConcurrentHashMap<>();


    // Not instantiable by external clients
    private CryptoRegistry() {

    }


    /**
     * Retrieves the singleton.
     *
     * @return The sole {@code CryptoRegistry} instance
     */
    public static CryptoRegistry instance() {

        return INSTANCE;
    }


    /**
     * Register a {@code CryptoOperations} object with this registry under the given name. If another object has
     * previously been registered under the same name, it will be overwritten.
     *
     * @param name The name of the registered object
     * @param ops The {@code CryptoOperations} object to register
     */
    public void register(String name, CryptoOperations<?> ops) {

        this.registry.put(name, ops);
    }


    /**
     * Registers a default {@code CryptoOperations} object. Typically this will be used by applications with simple
     * encryption requirements (e.g. using a consistent encryption configuration across the entire application).
     * However, it is perfectly valid to register named objects alongside a default one.
     *
     * @param ops The {@code CryptoOperations} to register as the default
     * @see #register(String, CryptoOperations)
     */
    public void registerDefault(CryptoOperations<?> ops) {

        this.register(DEFAULT_NAME, ops);
    }


    /**
     * Lookup the {@code CryptoOperations} object with the given name.
     *
     * @param <T> The expected {@code CryptoOperations} implementor or sub-interface
     * @param name The name of the registered object
     * @param type The expected type of the registered object
     * @return The {@code CryptoOperations} object that was previously registered under the given name
     * @throws CryptoBridgeConfigException if no object has been registered under the given name, or if the
     *         registered object is not of the expected type.
     * @see #register(String, CryptoOperations)
     */
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


    /**
     * Lookup the default {@code CryptoOperations} object.
     *
     * @param <T> The expected {@code CryptoOperations} implementor or sub-interface
     * @param type The expected type of the registered object
     * @return The default {@code CryptoOperations} object that was previously registered
     * @see #lookup(String, Class)
     */
    public <T extends CryptoOperations<?>> T lookupDefault(Class<T> type) {

        return this.lookup(DEFAULT_NAME, type);
    }


    /**
     * Resets the registry by clearing all registered {@code CryptoOperations}. Primarily used for testing purposes,
     * though it may prove useful for edge cases.
     */
    public void clear() {

        this.registry.clear();
    }
}
