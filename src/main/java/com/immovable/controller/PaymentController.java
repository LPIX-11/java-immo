package main.java.com.immovable.controller;

import main.java.com.immovable.models.YearMonth;
import main.java.com.immovable.models.Paiement;
import main.java.com.immovable.services.implementations.RentImpl;
import main.java.com.immovable.services.implementations.PaymentImpl;
import main.java.com.immovable.services.implementations.ParameterImpl;
import main.java.com.immovable.services.implementations.UserImpl;
import main.java.com.immovable.utils.Res;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Path("payments")
public class PaymentController extends BaseController {

    private final RentImpl rentImpl = new RentImpl();

    private final PaymentImpl paymentImpl = new PaymentImpl();

    private final UserImpl userImpl = new UserImpl();

    private final ParameterImpl parameterImpl = new ParameterImpl();

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
            paiement.setNumPaiement(Res.generateNumpaiement());
            paiement.setUser(userImpl.getUserById(user_id));
            paiement.setRent(rentImpl.getLocationById(location_id));
            paiement.setTypeReglement(parameterImpl.getTypeReglementbyId(typeReglement_id));

            List<YearMonth> lm=new ArrayList<>();


            for(int i=0;i<mois.size();i++){
                lm.add(parameterImpl.getMoisAnneeById(mois.get(i)));
            }
            boolean result = paymentImpl.addPaiement(paiement,lm);

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
        return sendSuccess("Tous les paiements", paymentImpl.getAllPaiements());
    }

    @GET
    @Path("/customer/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaiementsByIdClient(@PathParam("id") Long id){
        return sendSuccess("Tous les paiements du client id="+id, paymentImpl.getPaiementsByIdClient(id));
    }
}
