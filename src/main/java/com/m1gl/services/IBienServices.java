package main.java.com.m1gl.services;

import main.java.com.m1gl.models.Bailleur;
import main.java.com.m1gl.models.Bien;
import main.java.com.m1gl.models.Photo;
import main.java.com.m1gl.models.Typebien;

import java.util.List;

//@Local
public interface IBienServices {

    List<Bailleur> getAllBailleurs();

    List<Bailleur> getBailleursByCat(boolean categorie);

    boolean saveBailleur(Bailleur bailleur);

    boolean updateBailleur(Bailleur bailleur);

    Bailleur getBailleurByTel(String telephone);

    Bailleur getBailleurByEmail(String Email);

    Bailleur getBailleurById(Long id);

    List<Bien> getAllBiens();

    Bien getBienById(Long id);

    boolean saveBien(Bien bien);

    boolean updateBien(Bien bien);

    List<Photo> getAllPhoto();

    Photo getPhotoById(Long id);

    boolean savePhoto(Photo photo);

    boolean updatePhoto(Photo photo);

    boolean deletePhoto(Photo photo);

    Typebien getTypeBienById(Long id);

    List<Typebien> allTypeBien();

}
