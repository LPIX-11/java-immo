package main.java.com.m1gl.services.implementations;

import main.java.com.m1gl.config.HibernateConfiguration;
import main.java.com.m1gl.models.Location;
import main.java.com.m1gl.services.ILocationServices;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

//@Stateless
public class LocationDAO implements ILocationServices {

    private Session session = HibernateConfiguration.getSession();

    @Override
    public List<Location> all() {
        try {
            return session.createQuery("SELECT l from Location l", Location.class).getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Location getLocationById(Long id) {
        try{
            return session.createQuery("select l from Location l where l.id=:id",Location.class)
                    .setParameter("id",id).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public boolean updateLocation(Location location) {
        try{
            session.beginTransaction();
            session.update(location);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveLocation(Location location) {
        try{
            session.beginTransaction();
            session.save(location);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }
}
