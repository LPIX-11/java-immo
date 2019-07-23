package main.java.com.m1gl.services;

import main.java.com.m1gl.models.Annee;
import main.java.com.m1gl.models.Mois;
import main.java.com.m1gl.models.MoisAnnee;
import main.java.com.m1gl.models.TypeReglement;

import java.util.List;

//@Local
public interface IParametrage {

    boolean addyear(Annee annee);

    Annee getYearById(Long id);

    Annee getYearByLibelle(String libelle);

    List<Annee> getYears();

    List<Mois> getMois();

    List<MoisAnnee> getMonthYears();

    List<MoisAnnee> getMonthYearsByIdYear(Long id);

    MoisAnnee getMoisAnneeById(Long id);

    TypeReglement getTypeReglementbyId(Long id);


}
