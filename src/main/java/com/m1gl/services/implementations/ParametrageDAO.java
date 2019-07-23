package main.java.com.m1gl.services.implementations;

import main.java.com.m1gl.config.HibernateConfiguration;
import main.java.com.m1gl.models.Annee;
import main.java.com.m1gl.models.Mois;
import main.java.com.m1gl.models.MoisAnnee;
import main.java.com.m1gl.models.TypeReglement;
import main.java.com.m1gl.services.IParametrage;
import org.hibernate.Session;

import java.util.List;

//@Stateless
public class ParametrageDAO implements IParametrage {
    Session session = HibernateConfiguration.getSession();

    @Override
    public boolean addyear(Annee annee) {
        try {
            session.beginTransaction();
            session.save(annee);
            session.flush();
            List<Mois> mois=getMois();

            for (Mois m: mois) {
               session.save(new MoisAnnee(m,annee));
            }
            session.getTransaction().commit();
            return true;

        }catch (Exception ex){
            return false;
        }
    }

    @Override
    public Annee getYearById(Long id) {
        try{
            return session.createQuery("select a from Annee a where a.id=:id",Annee.class)
                    .setParameter("id",id).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public Annee getYearByLibelle(String libelle) {
        try{
            return session.createQuery("select a from Annee a where a.an=:libelle",Annee.class)
                    .setParameter("libelle",libelle).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public List<Annee> getYears() {
        return session.createQuery("select a from Annee a",Annee.class).getResultList();
    }

    @Override
    public List<Mois> getMois() {
        return session.createQuery("select m from Mois m",Mois.class).getResultList();
    }

    @Override
    public List<MoisAnnee> getMonthYears() {
        return session.createQuery("select m from MoisAnnee m",MoisAnnee.class).getResultList();
    }

    @Override
    public List<MoisAnnee> getMonthYearsByIdYear(Long id) {
        return session.createQuery("select m from MoisAnnee m where m.annee.id=:id",MoisAnnee.class)
                .setParameter("id",id).getResultList();
    }

    @Override
    public MoisAnnee getMoisAnneeById(Long id) {
        try{
            return session.createQuery("select m from MoisAnnee m where m.id=:id",MoisAnnee.class)
                    .setParameter("id",id).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public TypeReglement getTypeReglementbyId(Long id) {
        try{
            return session.createQuery("select t from TypeReglement t where t.id=:id",TypeReglement.class)
                    .setParameter("id",id).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }
}
