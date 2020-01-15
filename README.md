# AWS Encryption SDK Bridge

Defines integration layers and supporting libraries for integrating the AWS
Encryption SDK into some common Java-based frameworks.

- [ ] Core
- [ ] JPA
- [ ] Java EE 5+
- [ ] Spring
- [ ] Spring Boot Starter
- [ ] Hibernate
- [ ] Java Cryptography Extensions (JCE)
- [ ] Jasypt
- [ ] Migration Shim (for decrypting data with a legacy mechanism, but
      re-encrypting with AWS ESDK)

## Core

Defines higher-level interfaces for managing encryption and decryption
operations, along with wrappers around key AWS ESDK classes to adapt them to
those interfaces and make them easier to work with.

## JPA

`AttributeConverter` classes to encrypt various primitive and JDK types into
strings or raw byte arrays (the 2 types the ESDK encrypts to/decrypts from).

:question: Perhaps some kind of interface(s) that other bridges can implement?

:question: How dow we manage the acquisition/management of the underlying
encryption subsystem?

### Java EE 6+

CDI & JNDI for managing the subsystem.

## Spring

Spring Beans for managing the subsystem.

### Spring Boot Starter

Starter, with type-safe properties and auto-configuration

## Hibernate

UserTypes

:question: Which version(s) of Hibernate?

:question: Listeners?

## Java Cryptography Extensions (JCE)

:question: Is this even possible? Like any standard JDK extension point, it
seems very complicated.

However, it could simplify....

## Jasypt

This could be handy for migration, but I'm not sure. Might be extraneous, though
others might find it useful.

## Migration Shim

For our needs:

- Configuration of Jasypt & Bouncy Castle
- JPA Converters that attempt decryption through the ESDK, then fallback to
  Jasypt in the event of an exception
