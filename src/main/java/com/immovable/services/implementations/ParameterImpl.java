package main.java.com.immovable.services.implementations;

import main.java.com.immovable.config.HibernateConfiguration;
import main.java.com.immovable.models.Month;
import main.java.com.immovable.models.Year;
import main.java.com.immovable.models.YearMonth;
import main.java.com.immovable.models.TypeReglement;
import main.java.com.immovable.services.IParameterImpl;
import org.hibernate.Session;

import java.util.List;

//@Stateless
public class ParameterImpl implements IParameterImpl {
    Session session = HibernateConfiguration.getSession();

    @Override
    public boolean addyear(Year year) {
        try {
            session.beginTransaction();
            session.save(year);
            session.flush();
            List<Month> mois=getMois();

            for (Month m: mois) {
               session.save(new YearMonth(m, year));
            }
            session.getTransaction().commit();
            return true;

        }catch (Exception ex){
            return false;
        }
    }

    @Override
    public Year getYearById(Long id) {
        try{
            return session.createQuery("select a from Year a where a.id=:id", Year.class)
                    .setParameter("id",id).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public Year getYearByLibelle(String libelle) {
        try{
            return session.createQuery("select a from Year a where a.an=:libelle", Year.class)
                    .setParameter("libelle",libelle).getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public List<Year> getYears() {
        return session.createQuery("select a from Year a", Year.class).getResultList();
    }

    @Override
    public List<Month> getMois() {
        return session.createQuery("select m from Month m", Month.class).getResultList();
    }

    @Override
    public List<YearMonth> getMonthYears() {
        return session.createQuery("select m from YearMonth m", YearMonth.class).getResultList();
    }

    @Override
    public List<YearMonth> getMonthYearsByIdYear(Long id) {
        return session.createQuery("select m from YearMonth m where m.annee.id=:id", YearMonth.class)
                .setParameter("id",id).getResultList();
    }

    @Override
    public YearMonth getMoisAnneeById(Long id) {
        try{
            return session.createQuery("select m from YearMonth m where m.id=:id", YearMonth.class)
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
