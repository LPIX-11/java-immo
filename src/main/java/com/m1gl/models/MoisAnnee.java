package main.java.com.m1gl.models;

import javax.persistence.*;

@Entity
public class MoisAnnee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Mois mois;

    @ManyToOne
    private Annee annee;

    public MoisAnnee() {
    }

    public MoisAnnee(Mois mois, Annee annee) {
        this.mois = mois;
        this.annee = annee;
    }

    public MoisAnnee(Paiement paiement, MoisAnnee moisAnneeById) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mois getMois() {
        return mois;
    }

    public void setMois(Mois mois) {
        this.mois = mois;
    }

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }
}
