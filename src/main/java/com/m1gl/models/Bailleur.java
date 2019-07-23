package main.java.com.m1gl.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Bailleur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 80)
    private String bailleurNom;

    @Column(nullable = false,length = 100)
    private String email;

    @Column(nullable = false,length = 17)
    private String telephone;

    @Column(nullable = false,length = 100)
    private String adresse;

    @Column(nullable = false)
    private boolean bailleurCategorie;

    @Column(nullable = false,length = 30)
    private String numeroPiece;


    /*@OneToMany(mappedBy = "bailleur",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Bien> biensListe;
*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBailleurNom() {
        return bailleurNom;
    }

    public void setBailleurNom(String bailleurNom) {
        this.bailleurNom = bailleurNom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public boolean isBailleurCategorie() {
        return bailleurCategorie;
    }

    public void setBailleurCategorie(boolean bailleurCategorie) {
        this.bailleurCategorie = bailleurCategorie;
    }

    public String getNumeroPiece() {
        return numeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        this.numeroPiece = numeroPiece;
    }

   /* public List<Bien> getBiensListe() {
        return biensListe;
    }

    public void setBiensListe(List<Bien> biensListe) {
        this.biensListe = biensListe;
    }*/
}
