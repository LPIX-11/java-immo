package main.java.com.m1gl.models;


import javax.persistence.*;

@Entity
public class Annee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false,length = 4)
    private String an;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAn() {
        return an;
    }

    public void setAn(String an) {
        this.an = an;
    }
}
