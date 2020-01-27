package com.cyberscout.awscrypto.hibernate.entity;


import com.cyberscout.awscrypto.hibernate.LocalDateToEncryptedStringType;
import com.cyberscout.awscrypto.hibernate.Parameters;
import com.cyberscout.awscrypto.hibernate.StringToEncryptedStringType;
import lombok.Data;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;


@Data
@Entity
//@formatter:off
@TypeDefs({
    @TypeDef(
            name = "encryptedStringType",
            typeClass = StringToEncryptedStringType.class,
            parameters = { @Parameter(name = Parameters.CRYPTO_NAME, value = Constants.CRYPTO_NAME) }
    ),
    @TypeDef(
            name = "encryptedLocalDateType",
            typeClass = LocalDateToEncryptedStringType.class,
            parameters = { @Parameter(name = Parameters.CRYPTO_NAME, value = Constants.CRYPTO_NAME) }
    )
})
//@formatter:on
public class Employee {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfHire;
    @Type(type = "encryptedStringType")
    @Column(length = 2048)
    private String socialSecurityNumber;
    @Type(type = "encryptedLocalDateType")
    @Column(length = 2048)
    private LocalDate dateOfBirth;
}
