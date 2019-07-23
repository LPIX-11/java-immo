package main.java.com.m1gl.services.implementations;

import main.java.com.m1gl.config.HibernateConfiguration;
import main.java.com.m1gl.models.Client;
import main.java.com.m1gl.services.IClientServices;
import org.hibernate.Session;

import java.util.List;

//@Stateless
public class ClientDAO implements IClientServices {
    Session session= HibernateConfiguration.getSession();


    @Override
    public boolean addClient(Client client) {
        try {
            session.beginTransaction();
            session.save(client);
            session.getTransaction().commit();
            return true;
        } catch(Exception ex){
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public Client getClientByTel(String telephone) {
        try {
            return session.createQuery("SELECT c FROM Client c where c.telephone=:telephone",Client.class)
                    .setParameter("telephone",telephone).getSingleResult();
        }catch (Exception ex){
            return null;
        }

    }

    @Override
    public Client getClientByEmail(String email) {
        try {
            return session.createQuery("SELECT c FROM Client c where c.email=:email",Client.class)
                    .setParameter("email",email).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public Client getClientById(Long id) {
        try {
            return session.createQuery("SELECT c FROM Client c where c.id=:id",Client.class)
                    .setParameter("id",id).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public Client getClientByCIN(String cin) {
        return null;
    }

    @Override
    public List<Client> getAllClient() {
        try {
            return session.createQuery("SELECT c FROM Client c",Client.class).getResultList();
        }catch (Exception ex){
            return null;
        }
    }



    @Override
    public boolean updateClient(Client client) {
        try {
            session.beginTransaction();
            session.merge(client);
            session.getTransaction().commit();
            return true;
        } catch(Exception ex){
            session.getTransaction().rollback();
            return false;
        }
    }
}
