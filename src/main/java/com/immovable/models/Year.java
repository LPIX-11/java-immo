package main.java.com.immovable.models;


import javax.persistence.*;

@Entity
public class Year {

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
