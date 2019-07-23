package main.java.com.immovable.services.implementations;

import main.java.com.immovable.config.HibernateConfiguration;
import main.java.com.immovable.models.YearMonth;
import main.java.com.immovable.models.PaymentMonth;
import main.java.com.immovable.models.Paiement;
import main.java.com.immovable.services.IPaymentService;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

//@Stateless
public class PaymentImpl implements IPaymentService {

    private Session session= HibernateConfiguration.getSession();


    @Override
    public boolean addPaiement(Paiement paiement, List<YearMonth> moisAnnees) {
        try {
            session.beginTransaction();
            session.persist(paiement);

            session.flush();

            for (YearMonth yearMonth : moisAnnees) {

                session.persist(new PaymentMonth(yearMonth, paiement));
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
    public List<PaymentMonth> getAllMoisPaiementByIdClient(Long id) {
        return null;
    }
}
