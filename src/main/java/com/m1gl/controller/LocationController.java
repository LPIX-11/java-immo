package main.java.com.m1gl.controller;

import main.java.com.m1gl.models.Location;
import main.java.com.m1gl.services.implementations.LocationDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/locations")
public class LocationController extends BaseController {

    private final LocationDAO iLocationServices = new LocationDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLocation() {
        return sendSuccess("Liste des Locations", iLocationServices.all());
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public  Response getLocation(@PathParam("id") Long id){
        Location location = iLocationServices.getLocationById(id);

        if(location!=null)
        {
            return sendSuccess("location id " + id, location);
        }else {
            return sendError(404, "bien non trouvé");
        }

    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateLocation(@PathParam("id") Long id,Location newlocation){
        Location location = iLocationServices.getLocationById(id);

        if(location!=null)
        {
            location.setBien(newlocation.getBien());
            location.setClient(newlocation.getClient());
            location.setLocationDate(newlocation.getLocationDate());
            location.setMontantCaution(newlocation.getMontantCaution());
            location.setPrixLocation(newlocation.getPrixLocation());
            location.setUser(newlocation.getUser());
            if(iLocationServices.saveLocation(location)){

                return sendSuccess("location id " + id +" a ete mofiter avec success", location);
            }
            return sendError(200, "Erreur update");

        }else {
            return sendError(404, "bien non trouvé");
        }
    }

    /**
     *
     * @param location
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
    public  Response addLocation(Location location)
    {
        if(location.getLocationNum().trim().equals("")){
            return sendError(200, "Renseigner tous les champs!");
        }
        try {

            boolean save = iLocationServices.saveLocation(location);
            if (save) {
                return sendSuccess("location enregistré avec success!", location);
            } else {
                return sendError(200, "location non enregistré!");
            }
        }catch (Exception ex)
        {
            return sendError(500, "Erreur server");
        }
    }



}
