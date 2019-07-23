package main.java.com.immovable.controller;


import main.java.com.immovable.models.Customer;
import main.java.com.immovable.services.implementations.CustomerImpl;
import main.java.com.immovable.utils.Res;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Ce controller gère le CRUD du client, la liste des locations d'un client
 */
@Path("/customers")
public class CustomerController extends BaseController{

    private final CustomerImpl customerImpl = new CustomerImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClient(){
        return sendSuccess("Liste des clients", customerImpl.getAllClient());
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addClient(Customer customer){
        if(customer.getTelephone().trim().equals("") || customer.getCIN().trim().equals("") ||
        customer.getEmail().trim().equals("")  ||
        customer.getNom().trim().equals("") || customer.getPrenom().trim().equals("")){
            return sendError(200,"Veuillez renseigner tous les champs!");
        }else{
            if(customerImpl.getClientByTel(customer.getTelephone()) != null){
                return sendError(200,"Ce numéro de téléphone est déjà associée à un compte");
            }

            if(customerImpl.getClientByEmail(customer.getEmail()) != null){
                return sendError(200,"Cette adresse email est déjà associée à un compte");
            }

            if(customerImpl.getClientByEmail(customer.getCIN()) != null){
                return sendError(200,"Ce CIN est déjà associée à un compte");
            }

            try{
                customer.setMatricule(Res.generateMatriculeClient());
                boolean aide = customerImpl.addClient(customer);

                if(aide){
                    return sendSuccess("Customer ajouté avec succes", customer);
                }else{
                    return sendError(200,"Erreur survenue lors de l'ajout du customer");
                }

            }catch(Exception ex){

                return sendError(500,"Erreur server");
            }
        }
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response showClient(@PathParam("id") Long id){
        Customer customer = customerImpl.getClientById(id);

        if(customer !=null){
            return sendSuccess("Customer id= "+id, customer);
        }

        return sendError(200,"Customer non trouvé");
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateClient(@PathParam("id") Long id, Customer newclient){

        Customer customer = customerImpl.getClientById(id);

        if(customer !=null){
            customer.setCIN(newclient.getCIN());
            customer.setTelephone(newclient.getTelephone());
            customer.setEmail(newclient.getEmail());
            customer.setNom(newclient.getNom());
            customer.setPrenom(newclient.getPrenom());

            if(customerImpl.updateClient(customer)){
                return sendSuccess("Customer id= "+id+" modifié avec succés!", customer);
            }else{
                return sendError(200,"update error");
            }

        }

        return sendError(200,"Customer non trouvé, impossible de faire une modification");

    }






}
