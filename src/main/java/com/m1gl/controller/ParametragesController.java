package main.java.com.m1gl.controller;


import main.java.com.m1gl.models.Annee;
import main.java.com.m1gl.services.implementations.ParametrageDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Ce controller gere les mois, les annees, les mois-annees
 * les mois sont initialiser directement dans la base de donnees
 * chaque creation d'une annee va creer aussi 12 mois-annees en fonction de l'annee crée
 * C'est les mois-annees qui seront utilisés pour les paiements
 */
@Path("/parametrage")
public class ParametragesController extends BaseController{


    private final ParametrageDAO iparam = new ParametrageDAO();


    /**
     *
     * @param annee l'année à créer
     * @return succes et l'année créé si tout est ok et error en cas de problème
     */
    @POST
    @Path("/annees")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addYers(Annee annee){
        boolean response = iparam.addyear(annee);

        if(response){
            return sendSuccess("Année crée avec succés!",annee);
        }

        return sendError(200,"Erreur création année!");
    }

    /**
     *
     * @return la liste des 12 mois {octobre à Décembre}
     */

    @GET
    @Path("/mois")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMois(){
        return sendSuccess("Liste des mois",iparam.getMois());
    }



    /**
     *
     * @param id l'id de l'année qu'on veut récupérer ses 12 mois
     * @return Les liste des 12 mois-annees
     */

    @GET
    @Path("/moisannees/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMoisAnneesById(@PathParam("id") Long id){
        if(iparam.getMonthYearsByIdYear(id)!=null){
            return sendSuccess("Mois-Annees id= "+id,iparam.getMonthYearsByIdYear(id));
        }

        return sendError(404,"Ce Mois-Annees n'existe pas");
    }


}
