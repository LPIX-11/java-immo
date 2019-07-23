package main.java.com.m1gl.controller;


import main.java.com.m1gl.models.User;
import main.java.com.m1gl.services.implementations.UserDAO;
import main.java.com.m1gl.utils.Utilitaire;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UserController extends BaseController{


    private final UserDAO iuser = new UserDAO();



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listService() {
        List<User> users = iuser.getAllUsers();

        return sendSuccess("liste des utilisateurs",users);


    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(User user){

        try {
            if(user.getNom().trim().equals("") || user.getPrenom().trim().equals("") ||
                    user.getEmail().trim().equals("") || user.getUsername().trim().equals("") ||
                    user.getPassword().trim().equals("")){
                return sendError(200,"Renseigner tous les champs");
            }

            if(iuser.getUserByUsername(user.getUsername())!=null){
                return sendError(200,"Ce nom d'utilisateur est déjà associé à un compte");
            }

            if(iuser.getUserByEmail(user.getEmail())!=null){
                return sendError(200,"Cette adresse email est déjà associé à un compte");
            }

            user.setMatricule(Utilitaire.generateMatriculeUser());

            boolean resp = iuser.addUser(user);

            if(resp){
                return sendSuccess("Inscription reussi",user);
            }

            return sendError(200,"Erreur inscription utilisateur!");




        }catch (Exception ex){

            return sendError(500,"Erreur serveur de base de données!");
        }


    }

//    @POST
//    @Path("/login")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response login(User user){
//
//        try {
//            if(user.getUsername().trim().equals("") ||
//                    user.getPassword().trim().equals("")){
//                return sendError(200,"Renseigner tous les champs!");
//            }
//            User resp = iuser.login(user);
//
//            if(resp!=null){
//                return sendSuccess("Connexion reussie",resp);
//            }else{
//                return sendError(200,"Login ou mot de passe incorrect!");
//            }
//
//
//        }catch (Exception ex){
//            return sendError(200,"Erreur server");
//        }
//
//
//    }


    /**
     *
     * @param username nom d'utilisateur
     * @param password mot de passe
     * @return Objet User en cas success et null en cas error
     */

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response logine(@FormDataParam("username") String username, @FormDataParam("password") String password){

        try {
            if(username.trim().equals("") ||
                    password.trim().equals("")){
                return sendError(200,"Renseigner tous les champs!");
            }
            User resp = iuser.login(username,password);

            if(resp!=null){
                return sendSuccess("Connexion reussie",resp);
            }else{
                return sendError(200,"Login ou mot de passe incorrect!");
            }


        }catch (Exception ex){
            return sendError(200,"Erreur server");
        }


    }



}
