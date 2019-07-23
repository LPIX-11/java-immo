package main.java.com.m1gl.services;


import main.java.com.m1gl.models.User;

import java.util.List;

//@Local
public interface IUserServices {

    List<User> getAllUsers();

    boolean addUser(User user);


    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User login(String username,String password);

    User getUserById(Long id);
}
