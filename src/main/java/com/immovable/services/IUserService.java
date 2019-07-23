package main.java.com.immovable.services;


import main.java.com.immovable.models.User;

import java.util.List;

//@Local
public interface IUserService {

    List<User> getAllUsers();

    boolean addUser(User user);


    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User login(String username,String password);

    User getUserById(Long id);
}
