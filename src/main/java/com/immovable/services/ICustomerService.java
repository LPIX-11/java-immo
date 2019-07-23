package main.java.com.immovable.services;

import main.java.com.immovable.models.Customer;

import java.util.List;

//@Local
public interface ICustomerService {

    boolean addClient(Customer customer);

    Customer getClientByTel(String telephone);

    Customer getClientByEmail(String email);

    Customer getClientById(Long id);

    Customer getClientByCIN(String cin);

    List<Customer> getAllClient();



    boolean updateClient(Customer customer);

}
