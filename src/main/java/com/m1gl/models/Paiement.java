package main.java.com.m1gl.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Paiement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String numPaiement;

    @Temporal(TemporalType.DATE)
    private Date datePaiement;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne
    private TypeReglement typeReglement;

    @ManyToOne
    private Location location;

    public Paiement() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumPaiement() {
        return numPaiement;
    }

    public void setNumPaiement(String numPaiement) {
        this.numPaiement = numPaiement;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TypeReglement getTypeReglement() {
        return typeReglement;
    }

    public void setTypeReglement(TypeReglement typeReglement) {
        this.typeReglement = typeReglement;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
