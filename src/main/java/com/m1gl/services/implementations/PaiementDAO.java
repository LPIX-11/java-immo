package main.java.com.m1gl.services.implementations;

import main.java.com.m1gl.config.HibernateConfiguration;
import main.java.com.m1gl.models.MoisAnnee;
import main.java.com.m1gl.models.MoisPaiement;
import main.java.com.m1gl.models.Paiement;
import main.java.com.m1gl.services.IPaiement;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

//@Stateless
public class PaiementDAO implements IPaiement {

    private Session session= HibernateConfiguration.getSession();


    @Override
    public boolean addPaiement(Paiement paiement, List<MoisAnnee> moisAnnees) {
        try {
            session.beginTransaction();
            session.persist(paiement);

            session.flush();

            for (MoisAnnee moisAnnee : moisAnnees) {

                session.persist(new MoisPaiement(moisAnnee, paiement));
            }

            session.getTransaction().commit();
            return true;
        }catch(Exception ex){
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public List<Paiement> getPaiementsByIdMoisAnnee(Long id) {
        return null;
    }

    @Override
    public List<Paiement> getPaiementsByIdClient(Long id) {
        try {
            return session.createQuery("select p from Paiement p where p.location.client.id=:id",Paiement.class)
                    .getResultList();
        }catch (Exception ex){
            return new ArrayList<>();
        }
    }

    @Override
    public List<Paiement> getAllPaiements() {
        try{
            return session.createQuery("SELECT p FROM Paiement p",Paiement.class).getResultList();
        }catch (Exception ex){
            return new ArrayList<Paiement>();
        }
    }

    @Override
    public List<MoisPaiement> getAllMoisPaiementByIdClient(Long id) {
        return null;
    }
}
