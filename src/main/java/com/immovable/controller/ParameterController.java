package main.java.com.immovable.controller;

import main.java.com.immovable.models.Year;
import main.java.com.immovable.services.implementations.ParameterImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Ce controller gere les mois, les annees, les mois-annees les mois sont
 * initialiser directement dans la base de donnees chaque creation d'une annee
 * va creer aussi 12 mois-annees en fonction de l'annee crée C'est les
 * mois-annees qui seront utilisés pour les paiements
 */
@Path("/parameter")
public class ParameterController extends BaseController {

    private final ParameterImpl parameterImpl = new ParameterImpl();

    /**
     *
     * @param year l'année à créer
     * @return succes et l'année créé si tout est ok et error en cas de problème
     */
    @POST
    @Path("/years")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addYers(Year year) {
        boolean response = parameterImpl.addyear(year);

        if (response) {
            return sendSuccess("Année crée avec succés!", year);
        }

        return sendError(200, "Erreur création année!");
    }

    /**
     *
     * @return la liste des 12 mois {octobre à Décembre}
     */

    @GET
    @Path("/months")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMois() {
        return sendSuccess("Liste des mois", parameterImpl.getMois());
    }

    /**
     *
     * @param id l'id de l'année qu'on veut récupérer ses 12 mois
     * @return Les liste des 12 mois-annees
     */

    @GET
    @Path("/yearmonths/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMoisAnneesById(@PathParam("id") Long id) {
        if (parameterImpl.getMonthYearsByIdYear(id) != null) {
            return sendSuccess("Month-Annees id= " + id, parameterImpl.getMonthYearsByIdYear(id));
        }

        return sendError(404, "Ce Month-Annees n'existe pas");
    }

}
