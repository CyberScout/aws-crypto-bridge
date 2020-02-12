# AWS Encryption SDK Bridge

![CI Build](https://github.com/CyberScout/aws-crypto-bridge/workflows/CI%20Build/badge.svg)

Defines integration layers and supporting libraries for integrating the AWS
Encryption SDK into some common Java-based frameworks.

- [x] Core
- Hibernate
    - [x] Hibernate 3
    - [ ] Hibernate 4
    - [ ] Hibernate 5
- [ ] JPA
- Spring
    - [ ] Spring Core
    - [ ] Spring JPA
    - [ ] Spring Hibernate
    - [ ] Spring Boot Starter
- [ ] Java EE 5+
- [ ] Java Cryptography Extensions (JCE)
- [x] Jasypt
- [x] Migration Shim (for decrypting data with a legacy mechanism, but
     re-encrypting with AWS ESDK)

## Core

Defines higher-level interfaces for managing encryption and decryption
operations, along with wrappers around key AWS ESDK classes to adapt them to
those interfaces and make them easier to work with.

## Hibernate

`UserTypes`

:question: Which version(s) of Hibernate?

:exclamation: Starting with version 3, but since the UserType mechanism
underwent changes in both 4 and 5, we would need specific libraries for each of
those major versions (if we need/want comprehensive Hibernate support).

:question: Listeners?

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
