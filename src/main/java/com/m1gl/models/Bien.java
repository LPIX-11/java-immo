package main.java.com.m1gl.models;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


@Entity
public class Bien implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 40)
    private String bienNumero;

    @Column(nullable = false,length = 100)
    private String description;

    @Column(nullable = false)
    private int prixBailleur;

    @Column(nullable = false)
    private boolean statut;

    @Column(nullable = false)
    private double surface;

    private Timestamp dateAjout;

    @ManyToOne
    private Typebien typebien;


    @ManyToOne
    private Bailleur bailleur;

//    @OneToMany(mappedBy = "bien")
//    @JsonIgnore
//    private List<Location> locationList;

    @OneToMany(mappedBy = "bien")
    private List<Photo> photoList;



    public Bien() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBienNumero() {
        return bienNumero;
    }

    public void setBienNumero(String bienNumero) {
        this.bienNumero = bienNumero;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrixBailleur() {
        return prixBailleur;
    }

    public void setPrixBailleur(int prixBailleur) {
        this.prixBailleur = prixBailleur;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public Typebien getTypebien() {
        return typebien;
    }

    public void setTypebien(Typebien typebien) {
        this.typebien = typebien;
    }

    public Bailleur getBailleur() {
        return bailleur;
    }

    public void setBailleur(Bailleur bailleur) {
        this.bailleur = bailleur;
    }

/*    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }*/

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }

    public Timestamp getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Timestamp dateAjout) {
        this.dateAjout = dateAjout;
    }
}


