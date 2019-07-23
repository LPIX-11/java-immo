package main.java.com.m1gl.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Location implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 20)
    private String locationNum;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date locationDate;

    @Column(nullable = false)
    private Long montantCaution;

    @Column(nullable = false)
    private Long prixLocation;

    @ManyToOne
    private User user;

    @ManyToOne
    private Bien bien;

    @ManyToOne
    private Client client;

//    @OneToMany(mappedBy = "location")
//    private List<Paiement> paiement;

    public Location() {
    }



    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationNum() {
        return locationNum;
    }

    public void setLocationNum(String locationNum) {
        this.locationNum = locationNum;
    }

    public Date getLocationDate() {
        return locationDate;
    }

    public void setLocationDate(Date locationDate) {
        this.locationDate = locationDate;
    }

    public Long getMontantCaution() {
        return montantCaution;
    }

    public void setMontantCaution(Long montantCaution) {
        this.montantCaution = montantCaution;
    }

    public Long getPrixLocation() {
        return prixLocation;
    }

    public void setPrixLocation(Long prixLocation) {
        this.prixLocation = prixLocation;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
