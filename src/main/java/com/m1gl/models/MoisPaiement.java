package main.java.com.m1gl.models;

import javax.persistence.*;

@Entity
public class MoisPaiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MoisAnnee moisAnnee;

    @ManyToOne
    private Paiement paiement;

    public MoisPaiement(MoisAnnee moisAnnee, Paiement paiement) {
        this.moisAnnee = moisAnnee;
        this.paiement = paiement;
    }

    public MoisPaiement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MoisAnnee getMoisAnnee() {
        return moisAnnee;
    }

    public void setMoisAnnee(MoisAnnee moisAnnee) {
        this.moisAnnee = moisAnnee;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }
}
