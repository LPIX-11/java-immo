package main.java.com.immovable.services;

import main.java.com.immovable.models.Rent;

import java.util.List;

//@Local
public interface IRentService {
    List<Rent> all();

    Rent getLocationById(Long id);

    boolean updateLocation(Rent rent);

    boolean saveLocation(Rent rent);




}
