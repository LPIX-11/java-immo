package main.java.com.m1gl.controller;


import main.java.com.m1gl.models.Client;
import main.java.com.m1gl.services.implementations.ClientDAO;
import main.java.com.m1gl.utils.Utilitaire;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Ce controller gère le CRUD du client, la liste des locations d'un client
 */
@Path("/clients")
public class ClientController extends BaseController{

    private final ClientDAO iclient = new ClientDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClient(){
        return sendSuccess("Liste des clients",iclient.getAllClient());
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addClient(Client client){
        if(client.getTelephone().trim().equals("") || client.getCIN().trim().equals("") ||
        client.getEmail().trim().equals("")  ||
        client.getNom().trim().equals("") || client.getPrenom().trim().equals("")){
            return sendError(200,"Veuillez renseigner tous les champs!");
        }else{
            if(iclient.getClientByTel(client.getTelephone()) != null){
                return sendError(200,"Ce numéro de téléphone est déjà associée à un compte");
            }

            if(iclient.getClientByEmail(client.getEmail()) != null){
                return sendError(200,"Cette adresse email est déjà associée à un compte");
            }

            if(iclient.getClientByEmail(client.getCIN()) != null){
                return sendError(200,"Ce CIN est déjà associée à un compte");
            }

            try{
                client.setMatricule(Utilitaire.generateMatriculeClient());
                boolean aide = iclient.addClient(client);

                if(aide){
                    return sendSuccess("Client ajouté avec succes",client);
                }else{
                    return sendError(200,"Erreur survenue lors de l'ajout du client");
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
        Client client = iclient.getClientById(id);

        if(client!=null){
            return sendSuccess("Client id= "+id,client);
        }

        return sendError(200,"Client non trouvé");
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateClient(@PathParam("id") Long id,Client newclient){

        Client client = iclient.getClientById(id);

        if(client!=null){
            client.setCIN(newclient.getCIN());
            client.setTelephone(newclient.getTelephone());
            client.setEmail(newclient.getEmail());
            client.setNom(newclient.getNom());
            client.setPrenom(newclient.getPrenom());

            if(iclient.updateClient(client)){
                return sendSuccess("Client id= "+id+" modifié avec succés!",client);
            }else{
                return sendError(200,"update error");
            }

        }

        return sendError(200,"Client non trouvé, impossible de faire une modification");

    }






}
