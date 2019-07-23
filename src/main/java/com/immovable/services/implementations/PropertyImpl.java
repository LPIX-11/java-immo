package main.java.com.immovable.services.implementations;


import main.java.com.immovable.config.HibernateConfiguration;
import main.java.com.immovable.models.Lessor;
import main.java.com.immovable.models.Picture;
import main.java.com.immovable.models.Property;
import main.java.com.immovable.models.PropertyType;
import main.java.com.immovable.services.IPropertyService;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

//@Stateless
public class PropertyImpl implements IPropertyService {
    Session session = HibernateConfiguration.getSession();

    @Override
    public List<Lessor> getAllBailleurs() {
        List<Lessor> liste=null;

        try {
            liste=session.createQuery("SELECT b from Lessor b", Lessor.class).getResultList();
        }catch (Exception ex){
            throw ex;
        }

        return liste;
    }

    @Override
    public List<Lessor> getBailleursByCat(boolean categorie) {
        return session.
                createQuery("SELECT b from Lessor b where b.bailleurCategorie=:categorie", Lessor.class).
                setParameter("categorie",categorie)
                .getResultList();
    }

    @Override
    public boolean saveBailleur(Lessor lessor) {
        try{
            session.beginTransaction();
            session.save(lessor);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBailleur(Lessor lessor) {
        try{
            session.beginTransaction();
            session.update(lessor);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Lessor getBailleurByTel(String telephone) {
        try{
            return session.createQuery("select b from Lessor b where b.telephone=:telephone", Lessor.class)
                    .setParameter("telephone",telephone).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public Lessor getBailleurByEmail(String Email) {
        try{
            return session.createQuery("select b from Lessor b where b.email=:email", Lessor.class)
                    .setParameter("email",Email).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public Lessor getBailleurById(Long id) {
        try{
            return session.createQuery("select b from Lessor b where b.id=:id", Lessor.class)
                    .setParameter("id",id).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public List<Property> getAllBiens() {
        List<Property> liste=null;

        try {
            liste=session.createQuery("SELECT b from Property b", Property.class).getResultList();
        }catch (Exception ex){
            throw ex;
        }

        return liste;
    }

    @Override
    public Property getBienById(Long id) {
        try{
            return session.createQuery("select b from Property b where b.id=:id", Property.class)
                    .setParameter("id",id).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public boolean saveBien(Property property) {
        try{
            session.beginTransaction();
            session.save(property);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBien(Property property) {
        try{
            session.beginTransaction();
            session.update(property);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }



    @Override
    public List<Picture> getAllPhoto() {
        List<Picture> liste=null;

        try {
            liste=session.createQuery("SELECT b from Picture b", Picture.class).getResultList();
        }catch (Exception ex){
            throw ex;
        }

        return liste;
    }

    @Override
    public Picture getPhotoById(Long id) {
        try{
            return session.createQuery("select b from Picture b where b.id=:id", Picture.class)
                    .setParameter("id",id).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }


    @Override
    public boolean savePhoto(Picture picture) {
        try{
            session.beginTransaction();
            session.save(picture);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updatePhoto(Picture picture) {
        try{
            session.beginTransaction();
            session.update(picture);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletePhoto(Picture picture) {
        try{
            session.beginTransaction();
            session.delete(picture);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public PropertyType getTypeBienById(Long id) {
        try {
            return session.createQuery("select tb from PropertyType tb where tb.id=:id", PropertyType.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<PropertyType> allTypeBien() {
        try {
            return session.createQuery("select tb from PropertyType tb", PropertyType.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
