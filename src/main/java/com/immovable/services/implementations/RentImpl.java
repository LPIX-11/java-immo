package main.java.com.immovable.services.implementations;

import main.java.com.immovable.config.HibernateConfiguration;
import main.java.com.immovable.models.Rent;
import main.java.com.immovable.services.IRentService;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

//@Stateless
public class RentImpl implements IRentService {

    private Session session = HibernateConfiguration.getSession();

    @Override
    public List<Rent> all() {
        try {
            return session.createQuery("SELECT l from Rent l", Rent.class).getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Rent getLocationById(Long id) {
        try{
            return session.createQuery("select l from Rent l where l.id=:id", Rent.class)
                    .setParameter("id",id).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public boolean updateLocation(Rent rent) {
        try{
            session.beginTransaction();
            session.update(rent);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveLocation(Rent rent) {
        try{
            session.beginTransaction();
            session.save(rent);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }
}
