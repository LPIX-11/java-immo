package main.java.com.immovable.services;

import main.java.com.immovable.models.Lessor;
import main.java.com.immovable.models.Picture;
import main.java.com.immovable.models.Property;
import main.java.com.immovable.models.PropertyType;

import java.util.List;

//@Local
public interface IPropertyService {

    List<Lessor> getAllBailleurs();

    List<Lessor> getBailleursByCat(boolean categorie);

    boolean saveBailleur(Lessor lessor);

    boolean updateBailleur(Lessor lessor);

    Lessor getBailleurByTel(String telephone);

    Lessor getBailleurByEmail(String Email);

    Lessor getBailleurById(Long id);

    List<Property> getAllBiens();

    Property getBienById(Long id);

    boolean saveBien(Property property);

    boolean updateBien(Property property);

    List<Picture> getAllPhoto();

    Picture getPhotoById(Long id);

    boolean savePhoto(Picture picture);

    boolean updatePhoto(Picture picture);

    boolean deletePhoto(Picture picture);

    PropertyType getTypeBienById(Long id);

    List<PropertyType> allTypeBien();

}
