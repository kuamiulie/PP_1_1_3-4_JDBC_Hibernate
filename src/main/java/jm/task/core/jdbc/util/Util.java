package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {
    private static SessionFactory factory = null;

    public static Session getSessionFromConfig() {
        try {
            factory = new Configuration()
                    .configure("hibernate.cfg.xml").addAnnotatedClass(User.class).buildSessionFactory();
            return factory.openSession();
        } catch (Throwable ex) {
            System.err.println(" failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void close() {
        factory.close();
        factory.getCurrentSession().close();
    }
}
