package main.java.com.immovable.services;


import main.java.com.immovable.models.YearMonth;
import main.java.com.immovable.models.PaymentMonth;
import main.java.com.immovable.models.Paiement;

import java.util.List;

//@Local
public interface IPaymentService {

    boolean addPaiement(Paiement paiement, List<YearMonth> moisAnnees);

    List<Paiement> getPaiementsByIdMoisAnnee(Long id);

    List<Paiement> getPaiementsByIdClient(Long id);

    List<Paiement> getAllPaiements();


    List<PaymentMonth> getAllMoisPaiementByIdClient(Long id);
}
