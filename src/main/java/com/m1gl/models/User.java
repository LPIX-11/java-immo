package main.java.com.m1gl.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "utilisateur")
public class User extends Personne implements Serializable {
    @Column(length = 80,nullable = false)
    private String username;

    @Column(length = 80,nullable = false)
    private String password;



    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
