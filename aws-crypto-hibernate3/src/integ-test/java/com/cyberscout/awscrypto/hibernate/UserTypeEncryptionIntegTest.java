package com.cyberscout.awscrypto.hibernate;


import com.amazonaws.encryptionsdk.jce.JceMasterKey;
import com.cyberscout.awscrypto.core.CryptoRegistry;
import com.cyberscout.awscrypto.core.aws.AwsCryptoBase64Adaptor;
import com.cyberscout.awscrypto.core.aws.AwsCryptoFacade;
import com.cyberscout.awscrypto.core.aws.test.CryptoTestUtils;
import com.cyberscout.awscrypto.hibernate.entity.Constants;
import com.cyberscout.awscrypto.hibernate.entity.Employee;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class UserTypeEncryptionIntegTest {

    @Rule
    public final SessionFactoryRule sf = new SessionFactoryRule();


    @Before
    public void initEncryption() {

        JceMasterKey masterKey = CryptoTestUtils.createJceMasterKey(this.getClass().getSimpleName());
        AwsCryptoFacade<JceMasterKey> cryptoFacade = AwsCryptoFacade.forMasterKeyProvider(masterKey).build();
        CryptoRegistry.instance().register(Constants.CRYPTO_NAME, new AwsCryptoBase64Adaptor(cryptoFacade));
    }


    @After
    public void cleanUp() {

        Session session = sf.getSession();
        session.createSQLQuery("delete from employee").executeUpdate();
    }


    @Test
    public void micCheck() {

        final Long id = 1L;
        Session session = sf.getSession();
        Employee carroll = new Employee();
        carroll.setId(id);
        carroll.setFirstName("Carroll");
        carroll.setLastName("Shelby");
        carroll.setDateOfHire(LocalDate.of(2010, Month.JULY, 16));
        carroll.setSocialSecurityNumber("777441111");
        carroll.setDateOfBirth(LocalDate.of(1923, Month.JANUARY, 11));
        session.save(carroll);
        session.flush();

        session.clear();
        Employee res = (Employee) session.get(Employee.class, id);
        assertThat(res.getId(), is(carroll.getId()));
        assertThat(res.getFirstName(), is(carroll.getFirstName()));

        session.delete(res);
        session.flush();
    }


    @Test
    public void checkEncryptedValues() {

        final Long id = 10L;
        Session session = sf.getSession();
        Employee kobe = new Employee();
        kobe.setId(id);
        kobe.setFirstName("Kobe");
        kobe.setLastName("Bryant");
        kobe.setDateOfHire(LocalDate.of(2020, Month.JANUARY, 26));
        kobe.setSocialSecurityNumber("824052010");
        kobe.setDateOfBirth(LocalDate.of(1978, Month.AUGUST, 23));
        session.save(kobe);
        session.flush();

        session.clear();
        checkEncryptedValuesInDb(id, kobe.getSocialSecurityNumber(), kobe.getDateOfBirth());
        Employee res = (Employee) session.get(Employee.class, id);
        assertThat(res.getSocialSecurityNumber(), is(kobe.getSocialSecurityNumber()));
        assertThat(res.getDateOfBirth(), is(kobe.getDateOfBirth()));
    }


    private void checkEncryptedValuesInDb(long id, String ssn, LocalDate dob) {

        sf.getSession().doWork((connection) -> {
            @SuppressWarnings("SqlNoDataSourceInspection")
            PreparedStatement stmt = connection.prepareStatement(
                    "select socialSecurityNumber, dateOfBirth from employee where id = ?");
            stmt.setLong(1, id);
            ResultSet results = stmt.executeQuery();
            assertTrue(results.next());

            // Since encryption is not predictable, we can't be very specific in the assertions below...

            String dbSsn = results.getString(1);
            System.out.println(dbSsn);
            assertNotNull(dbSsn);
            assertThat(dbSsn, not(equalTo(ssn)));
            assertTrue(dbSsn.length() > 100);

            String dbDob = results.getString(2);
            System.out.println(dbDob);
            assertNotNull(dbDob);
            assertThat(dbDob, not(containsString(dob.format(DateTimeFormatter.ISO_LOCAL_DATE))));
            assertTrue(dbDob.length() > 100);

            assertThat(dbSsn, not(equalTo(dbDob)));
        });
    }
}
