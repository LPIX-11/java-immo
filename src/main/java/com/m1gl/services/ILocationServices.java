package main.java.com.m1gl.services;

import main.java.com.m1gl.models.Location;

import java.util.List;

//@Local
public interface ILocationServices {
    List<Location> all();

    Location getLocationById(Long id);

    boolean updateLocation(Location location);

    boolean saveLocation(Location location);




}
