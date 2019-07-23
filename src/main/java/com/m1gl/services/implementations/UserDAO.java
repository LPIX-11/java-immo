package main.java.com.m1gl.services.implementations;

import main.java.com.m1gl.config.HibernateConfiguration;
import main.java.com.m1gl.models.User;
import main.java.com.m1gl.services.IUserServices;
import org.hibernate.Session;

import java.util.List;

//@Stateless
public class UserDAO implements IUserServices {
    Session session = HibernateConfiguration.getSession();

    @Override
    public List<User> getAllUsers() {
        try {
            return session.createQuery("SELECT u FROM User u",User.class).getResultList();
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addUser(User user) {
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }
    }


    @Override
    public User getUserByUsername(String username) {

        try {
            return session.createQuery("SELECT u FROM User u Where u.username=:username",User.class).
                    setParameter("username",username).
                    getSingleResult();
        }catch (Exception ex){
            return null;
        }

    }

    @Override
    public User getUserByEmail(String email) {

        try {
            return  session.createQuery("SELECT p FROM User p Where p.email=:email", User.class).
                    setParameter("email",email).
                    getSingleResult();
        }catch (Exception ex){
            return null;
        }

    }

    @Override
    public User login(String username, String password) {
        try {
            return session.createQuery("SELECT u FROM User u Where u.username=:username AND u.password=:password",User.class).
                    setParameter("username",username).setParameter("password",password).
                    getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public User getUserById(Long id) {
        try {
            return  session.createQuery("SELECT u FROM User u Where u.id=:id", User.class).
                    setParameter("id",id).
                    getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }
}
