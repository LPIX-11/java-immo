package main.java.com.m1gl.models;


import javax.persistence.*;

@Entity
public class TypeReglement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String libelleType;

    public TypeReglement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleType() {
        return libelleType;
    }

    public void setLibelleType(String libelleType) {
        this.libelleType = libelleType;
    }
}
