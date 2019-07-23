package main.java.com.m1gl.services.implementations;


import main.java.com.m1gl.config.HibernateConfiguration;
import main.java.com.m1gl.models.Bailleur;
import main.java.com.m1gl.models.Bien;
import main.java.com.m1gl.models.Photo;
import main.java.com.m1gl.models.Typebien;
import main.java.com.m1gl.services.IBienServices;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

//@Stateless
public class BienDAO implements IBienServices {
    Session session = HibernateConfiguration.getSession();

    @Override
    public List<Bailleur> getAllBailleurs() {
        List<Bailleur> liste=null;

        try {
            liste=session.createQuery("SELECT b from Bailleur b",Bailleur.class).getResultList();
        }catch (Exception ex){
            throw ex;
        }

        return liste;
    }

    @Override
    public List<Bailleur> getBailleursByCat(boolean categorie) {
        return session.
                createQuery("SELECT b from Bailleur b where b.bailleurCategorie=:categorie",Bailleur.class).
                setParameter("categorie",categorie)
                .getResultList();
    }

    @Override
    public boolean saveBailleur(Bailleur bailleur) {
        try{
            session.beginTransaction();
            session.save(bailleur);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBailleur(Bailleur bailleur) {
        try{
            session.beginTransaction();
            session.update(bailleur);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Bailleur getBailleurByTel(String telephone) {
        try{
            return session.createQuery("select b from Bailleur b where b.telephone=:telephone",Bailleur.class)
                    .setParameter("telephone",telephone).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public Bailleur getBailleurByEmail(String Email) {
        try{
            return session.createQuery("select b from Bailleur b where b.email=:email",Bailleur.class)
                    .setParameter("email",Email).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public Bailleur getBailleurById(Long id) {
        try{
            return session.createQuery("select b from Bailleur b where b.id=:id",Bailleur.class)
                    .setParameter("id",id).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public List<Bien> getAllBiens() {
        List<Bien> liste=null;

        try {
            liste=session.createQuery("SELECT b from Bien b",Bien.class).getResultList();
        }catch (Exception ex){
            throw ex;
        }

        return liste;
    }

    @Override
    public Bien getBienById(Long id) {
        try{
            return session.createQuery("select b from Bien b where b.id=:id",Bien.class)
                    .setParameter("id",id).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public boolean saveBien(Bien bien) {
        try{
            session.beginTransaction();
            session.save(bien);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBien(Bien bien) {
        try{
            session.beginTransaction();
            session.update(bien);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }



    @Override
    public List<Photo> getAllPhoto() {
        List<Photo> liste=null;

        try {
            liste=session.createQuery("SELECT b from Photo b",Photo.class).getResultList();
        }catch (Exception ex){
            throw ex;
        }

        return liste;
    }

    @Override
    public Photo getPhotoById(Long id) {
        try{
            return session.createQuery("select b from Photo b where b.id=:id",Photo.class)
                    .setParameter("id",id).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }


    @Override
    public boolean savePhoto(Photo photo) {
        try{
            session.beginTransaction();
            session.save(photo);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updatePhoto(Photo photo) {
        try{
            session.beginTransaction();
            session.update(photo);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletePhoto(Photo photo) {
        try{
            session.beginTransaction();
            session.delete(photo);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Typebien getTypeBienById(Long id) {
        try {
            return session.createQuery("select tb from Typebien tb where tb.id=:id", Typebien.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Typebien> allTypeBien() {
        try {
            return session.createQuery("select tb from Typebien tb", Typebien.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
