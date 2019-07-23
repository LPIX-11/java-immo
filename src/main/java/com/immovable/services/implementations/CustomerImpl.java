package main.java.com.immovable.services.implementations;

import main.java.com.immovable.config.HibernateConfiguration;
import main.java.com.immovable.models.Customer;
import main.java.com.immovable.services.ICustomerService;
import org.hibernate.Session;

import java.util.List;

//@Stateless
public class CustomerImpl implements ICustomerService {
    Session session= HibernateConfiguration.getSession();


    @Override
    public boolean addClient(Customer customer) {
        try {
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
            return true;
        } catch(Exception ex){
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public Customer getClientByTel(String telephone) {
        try {
            return session.createQuery("SELECT c FROM Customer c where c.telephone=:telephone", Customer.class)
                    .setParameter("telephone",telephone).getSingleResult();
        }catch (Exception ex){
            return null;
        }

    }

    @Override
    public Customer getClientByEmail(String email) {
        try {
            return session.createQuery("SELECT c FROM Customer c where c.email=:email", Customer.class)
                    .setParameter("email",email).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public Customer getClientById(Long id) {
        try {
            return session.createQuery("SELECT c FROM Customer c where c.id=:id", Customer.class)
                    .setParameter("id",id).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public Customer getClientByCIN(String cin) {
        return null;
    }

    @Override
    public List<Customer> getAllClient() {
        try {
            return session.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        }catch (Exception ex){
            return null;
        }
    }



    @Override
    public boolean updateClient(Customer customer) {
        try {
            session.beginTransaction();
            session.merge(customer);
            session.getTransaction().commit();
            return true;
        } catch(Exception ex){
            session.getTransaction().rollback();
            return false;
        }
    }
}
