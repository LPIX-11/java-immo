package main.java.com.m1gl.services;


import main.java.com.m1gl.models.MoisAnnee;
import main.java.com.m1gl.models.MoisPaiement;
import main.java.com.m1gl.models.Paiement;

import java.util.List;

//@Local
public interface IPaiement {

    boolean addPaiement(Paiement paiement, List<MoisAnnee> moisAnnees);

    List<Paiement> getPaiementsByIdMoisAnnee(Long id);

    List<Paiement> getPaiementsByIdClient(Long id);

    List<Paiement> getAllPaiements();


    List<MoisPaiement> getAllMoisPaiementByIdClient(Long id);
}
