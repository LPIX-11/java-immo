package main.java.com.m1gl.controller;

import main.java.com.m1gl.models.MoisAnnee;
import main.java.com.m1gl.models.Paiement;
import main.java.com.m1gl.services.implementations.LocationDAO;
import main.java.com.m1gl.services.implementations.PaiementDAO;
import main.java.com.m1gl.services.implementations.ParametrageDAO;
import main.java.com.m1gl.services.implementations.UserDAO;
import main.java.com.m1gl.utils.Utilitaire;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Path("paiements")
public class PaiementController extends BaseController {

    private final LocationDAO ilocation = new LocationDAO();

    private final PaiementDAO ipaiement = new PaiementDAO();

    private final UserDAO iuser = new UserDAO();

    private final ParametrageDAO iparam = new ParametrageDAO();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addPaiement(@FormParam("location_id") Long location_id,
            @FormParam("typeReglement_id") Long typeReglement_id,
            @FormParam("user_id") Long user_id,
            @FormParam("listeMoisAnnees") List<Long> mois){


        try{
            Paiement paiement=new Paiement();
            paiement.setDatePaiement(java.sql.Date.valueOf(LocalDate.now()));
            paiement.setNumPaiement(Utilitaire.generateNumpaiement());
            paiement.setUser(iuser.getUserById(user_id));
            paiement.setLocation(ilocation.getLocationById(location_id));
            paiement.setTypeReglement(iparam.getTypeReglementbyId(typeReglement_id));

            List<MoisAnnee> lm=new ArrayList<>();


            for(int i=0;i<mois.size();i++){
                lm.add(iparam.getMoisAnneeById(mois.get(i)));
            }
            boolean result = ipaiement.addPaiement(paiement,lm);

            if(result) {
                return sendSuccess("Insertion paiement reussie!", paiement);
            }
            return sendError(200,"Erreur insertion paiement");
        }catch (Exception ex){
            ex.printStackTrace();
            return sendError(500,"Erreur Serveur");
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaiements(){
        return sendSuccess("Tous les paiements",ipaiement.getAllPaiements());
    }

    @GET
    @Path("/client/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaiementsByIdClient(@PathParam("id") Long id){
        return sendSuccess("Tous les paiements du client id="+id,ipaiement.getPaiementsByIdClient(id));
    }
}
