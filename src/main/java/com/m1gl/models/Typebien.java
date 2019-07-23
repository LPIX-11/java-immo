package main.java.com.m1gl.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Typebien implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String libelle;



    public Typebien(String libelle) {
        this.libelle = libelle;
    }

    public Typebien() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }


}
