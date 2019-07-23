package main.java.com.immovable.services;

import main.java.com.immovable.models.Month;
import main.java.com.immovable.models.Year;
import main.java.com.immovable.models.YearMonth;
import main.java.com.immovable.models.TypeReglement;

import java.util.List;

//@Local
public interface IParameterImpl {

    boolean addyear(Year year);

    Year getYearById(Long id);

    Year getYearByLibelle(String libelle);

    List<Year> getYears();

    List<Month> getMois();

    List<YearMonth> getMonthYears();

    List<YearMonth> getMonthYearsByIdYear(Long id);

    YearMonth getMoisAnneeById(Long id);

    TypeReglement getTypeReglementbyId(Long id);


}
