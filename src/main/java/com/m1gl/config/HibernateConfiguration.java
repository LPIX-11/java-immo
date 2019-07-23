package main.java.com.m1gl.config;

import main.java.com.m1gl.models.User;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;

public class HibernateConfiguration {

    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("/hibernate-postgres.cfg.xml");

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }



    public void initDatabase() throws Exception {
        final Session session = getSession();
        try {
            System.out.println("querying all the managed entities...");
            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                final String entityName = entityType.getName();
                final Query query = session.createQuery("from " + entityName);
                System.out.println("executing: " + query.getQueryString());
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
            }
            try {
                User user1 = session.createQuery("select u from User u where u.username like 'admin'", User.class).getSingleResult();
                if (user1 == null) {
                    User user = new User();
                    user.setMatricule("admin");
                    user.setEmail("admin@mail.com");
                    user.setPrenom("Admin");
                    user.setNom("Admin");
                    user.setUsername("admin");
                    user.setPassword("admin@123");
                    session.beginTransaction();
                    session.save(user);
                    session.getTransaction().commit();
                }
            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
    }
}
