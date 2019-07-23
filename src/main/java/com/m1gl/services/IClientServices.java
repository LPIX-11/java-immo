package main.java.com.m1gl.services;

import main.java.com.m1gl.models.Client;

import java.util.List;

//@Local
public interface IClientServices {

    boolean addClient(Client client);

    Client getClientByTel(String telephone);

    Client getClientByEmail(String email);

    Client getClientById(Long id);

    Client getClientByCIN(String cin);

    List<Client> getAllClient();



    boolean updateClient(Client client);

}
