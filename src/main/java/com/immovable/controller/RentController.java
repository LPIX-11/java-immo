package main.java.com.immovable.controller;

import main.java.com.immovable.models.Rent;
import main.java.com.immovable.services.implementations.RentImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/rent")
public class RentController extends BaseController {

    private final RentImpl rentImpl = new RentImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLocation() {
        return sendSuccess("Liste des Locations", rentImpl.all());
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public  Response getLocation(@PathParam("id") Long id){
        Rent rent = rentImpl.getLocationById(id);

        if(rent !=null)
        {
            return sendSuccess("rent id " + id, rent);
        }else {
            return sendError(404, "bien non trouvé");
        }

    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateLocation(@PathParam("id") Long id, Rent newlocation){
        Rent rent = rentImpl.getLocationById(id);

        if(rent !=null)
        {
            rent.setProperty(newlocation.getProperty());
            rent.setCustomer(newlocation.getCustomer());
            rent.setLocationDate(newlocation.getLocationDate());
            rent.setMontantCaution(newlocation.getMontantCaution());
            rent.setPrixLocation(newlocation.getPrixLocation());
            rent.setUser(newlocation.getUser());
            if(rentImpl.saveLocation(rent)){

                return sendSuccess("rent id " + id +" a ete mofiter avec success", rent);
            }
            return sendError(200, "Erreur update");

        }else {
            return sendError(404, "bien non trouvé");
        }
    }

    /**
     *
     * @param rent
     * @return
     * @apiNote
     * url: http://localhost:8080/BackendImmo_war_exploded/api/locations/1
     *  {
     *     "locationNum":"54324",
     *     "locationDate":"2019-07-12T00:00:00Z[UTC]",
     *     "montantCaution": 15000,
     *     "prixLocation":1500,
     *     "user":{
     *     	"id":1
     *     },
     *     "bien":{
     *     	"id":1
     *     },
     *     "client":{
     *     	"id":2
     *     }
     * }
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public  Response addLocation(Rent rent)
    {
        if(rent.getLocationNum().trim().equals("")){
            return sendError(200, "Renseigner tous les champs!");
        }
        try {

            boolean save = rentImpl.saveLocation(rent);
            if (save) {
                return sendSuccess("rent enregistré avec success!", rent);
            } else {
                return sendError(200, "rent non enregistré!");
            }
        }catch (Exception ex)
        {
            return sendError(500, "Erreur server");
        }
    }



}
