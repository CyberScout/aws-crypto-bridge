package com.cyberscout.awscrypto.hibernate;


import com.cyberscout.awscrypto.hibernate.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;


public class SessionFactoryRule implements MethodRule {

    private SessionFactory sessionFactory;
    private Transaction transaction;
    private Session session;


    @Override
    public Statement apply(final Statement statement, FrameworkMethod method, Object test) {

        return new Statement() {

            @Override
            public void evaluate() throws Throwable {

                sessionFactory = createSessionFactory();
                createSession();
                beginTransaction();
                try {
                    statement.evaluate();
                }
                finally {
                    shutdown();
                }
            }
        };
    }


    private void shutdown() {

        try {
            try {
                try {
                    transaction.rollback();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                session.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            sessionFactory.close();
        }
        catch (Exception ex) {

            ex.printStackTrace();
        }
    }


    private SessionFactory createSessionFactory() {

        Configuration configuration = new Configuration()
                .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
                .setProperty("hibernate.connection.url",
                             "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
                .setProperty("hibernate.hbm2ddl.auto", "create")
                .setProperty("hibernate.show_sql", "true")
                .addAnnotatedClass(Employee.class);
        return configuration.buildSessionFactory();
    }


    public Session createSession() {

        session = sessionFactory.openSession();
        return session;
    }


    public void commit() {

        transaction.commit();
    }


    public void beginTransaction() {

        transaction = session.beginTransaction();
    }


    public Session getSession() {

        return session;
    }
}
