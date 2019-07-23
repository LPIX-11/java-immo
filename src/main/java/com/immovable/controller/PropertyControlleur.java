package main.java.com.immovable.controller;


import main.java.com.immovable.models.Lessor;
import main.java.com.immovable.models.Property;
import main.java.com.immovable.models.Picture;
import main.java.com.immovable.models.PropertyType;
import main.java.com.immovable.services.implementations.PropertyImpl;
import main.java.com.immovable.utils.Res;
import org.glassfish.jersey.media.multipart.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Ce controller gère le CRUD du bailleur, le CRUD du bien
 */

@Path("/properties")
public class PropertyControlleur extends BaseController {

    private final String ALL_FIELD_REQUIRE = "Fill in all fields!";
    private final String ERROR_SERVER = "Server error";

    //    @EJB
    private final PropertyImpl propertyImpl = new PropertyImpl();


    /**
     * @return la Properties List
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllbiens() {
        List<Property> list = propertyImpl.getAllBiens();
        return sendSuccess("Properties List", list);
    }

    /**
     * @return la liste des types de biens
     */
    @GET
    @Path("/all-properties-type")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTypeBiens() {
        List<PropertyType> liste = propertyImpl.allTypeBien();
        return sendSuccess("Properties Type List", liste);
    }

    /**
     * @return les infotmation  d'un Biens
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBiens(@PathParam("id") Long id) {
        Property property = propertyImpl.getBienById(id);

        if (property != null) {
            return sendSuccess("Property id " + id, property);
        } else {
            return sendError(404, "Property not found");
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addBien(@FormDataParam("file") FormDataBodyPart body,
                            @FormDataParam("description") String description, @FormDataParam("prix_bailleur") int prix_bailleur,
                            @FormDataParam("surface") Double surface, @FormDataParam("bailleur_id") Long bailleur,
                            @FormDataParam("typebien_id") Long typebien) throws IOException {
        if (description.trim().equals("")) {
            return sendError(200, ALL_FIELD_REQUIRE);
        }
        Lessor lessor1 = propertyImpl.getBailleurById(bailleur);
        PropertyType propertyType1 = propertyImpl.getTypeBienById(typebien);
        List<Picture> photos = new ArrayList<>();
        Res res = new Res();

        for (BodyPart part : body.getParent().getBodyParts()) {
            InputStream is = part.getEntityAs(InputStream.class);
            ContentDisposition meta = part.getContentDisposition();
            if (meta.getFileName() != null) {
                Picture picture = res.writeToFile(is, meta);
                photos.add(picture);
            }
        }
        try {
            Property property = new Property();
            property.setBienNumero(Res.generateNumBien());
            property.setDescription(description);
            property.setSurface(surface);
            property.setStatut(true);
            property.setLessor(lessor1);
            property.setPropertyType(propertyType1);
            property.setPrixBailleur(prix_bailleur);
            boolean save = propertyImpl.saveBien(property);
            if (save) {
                if (!photos.isEmpty()) {
                    photos.forEach(x -> {
                        // x.setBien(property);
                        propertyImpl.savePhoto(x);
                    });
                    return sendSuccess("Property and picture(s) registered", property);
                } else {
                    return sendSuccess("Property registered but failed to load picture!", property);
                }
            } else {
                return sendError(200, "Property not registered");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return sendError(500, ERROR_SERVER);
        }
    }

    @POST
    @Path("/add-new-property")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addBienNew(@FormDataParam("photo") String photo,
                               @FormDataParam("description") String description, @FormDataParam("prix_bailleur") int prix_bailleur,
                               @FormDataParam("surface") Double surface, @FormDataParam("bailleur_id") Long bailleur,
                               @FormDataParam("typebien_id") Long typebien) throws IOException {
        if (description.trim().equals("")) {
            return sendError(200, ALL_FIELD_REQUIRE);
        }
        Lessor lessor1 = propertyImpl.getBailleurById(bailleur);
        PropertyType propertyType1 = propertyImpl.getTypeBienById(typebien);

        try {
            Property property = new Property();
            property.setBienNumero(Res.generateNumBien());
            property.setDescription(description);
            property.setSurface(surface);
            property.setStatut(true);
            property.setDateAjout(Timestamp.valueOf(LocalDateTime.now()));
            property.setLessor(lessor1);
            property.setPropertyType(propertyType1);
            property.setPrixBailleur(prix_bailleur);
            boolean save = propertyImpl.saveBien(property);
            if (save) {
                Picture picture1 = new Picture();
                picture1.setPath(photo);
                picture1.setProperty(property);
                boolean savePhoto = propertyImpl.savePhoto(picture1);
                if (savePhoto) {
                    return sendSuccess("Property et photo(s) enregistré avec success", propertyImpl.getAllBiens());
                } else {
                    return sendSuccess("Property enregistré avec success & error on upload picture!", propertyImpl.getAllBiens());
                }
            } else {
                return sendError(200, "Property non enregistré!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return sendError(500, ERROR_SERVER);
        }
    }


    /**
     * @param id     : identifant du biens
     * @param upbien :modification du bien
     * @return
     * @apiNote url : http://localhost:8080/BackendImmo_war_exploded/api/biens/1
     * }
     */

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBien(@PathParam("id") Long id, Property upbien) {
        Property bienold = propertyImpl.getBienById(id);
        if (bienold != null) {
            bienold.setBienNumero(upbien.getBienNumero());
            bienold.setDescription(upbien.getDescription());
            bienold.setPrixBailleur(upbien.getPrixBailleur());
            bienold.setStatut(upbien.isStatut());
            bienold.setPropertyType(upbien.getPropertyType());
            bienold.setLessor(upbien.getLessor());

            if (propertyImpl.updateBien(bienold)) {
                return sendSuccess("Property id " + id + " modifié avec succés!", upbien);
            }

            return sendError(200, "Erreur update");
        } else {
            return sendError(404, "Property non trouvé, impossible de faire une modification");
        }

    }


    /**
     * @return la liste des bailleurs
     */
    @GET
    @Path("/lessors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBailleurs() {
        List<Lessor> liste = propertyImpl.getAllBailleurs();
        return sendSuccess("Liste des Bailleurs", liste);
    }


    /**
     * @param lessor le lessor qu'on veut ajouter
     * @return succes et le lessor créé si tout est ok et error en cas de problème
     */

    @POST
    @Path("/lessors")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBailleur(Lessor lessor) {
        lessor.setBailleurCategorie(true);
        if (lessor.getAdresse().trim().equals("") || lessor.getEmail().trim().equals("") ||
                lessor.getNumeroPiece().trim().equals("") || lessor.getTelephone().trim().equals("") ||
                lessor.getBailleurNom().trim().equals("")) {
            return sendError(200, ALL_FIELD_REQUIRE);
        } else {
            if (propertyImpl.getBailleurByTel(lessor.getTelephone()) != null) {
                return sendError(200, "Ce numero de telephone est déjà associé à un lessor");
            }

            if (propertyImpl.getBailleurByEmail(lessor.getEmail()) != null) {
                return sendError(200, "Cette adresse email est déjà associé à un lessor");
            }
        }

        try {
            boolean save = propertyImpl.saveBailleur(lessor);

            if (save) {
                return sendSuccess("Lessor enregistré avec success!", lessor);
            } else {
                return sendError(200, "Lessor non enregistré!");
            }
        } catch (Exception ex) {
            return sendError(500, ERROR_SERVER);
        }
    }

    /**
     * @param id l'ID du bailleur qu'on veut récupérer
     * @return l'objet bailleur en cas de succes et null si bailleur n'existe pas
     */

    @GET
    @Path("lessor/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getBailleurById(@PathParam("id") Long id) {

        Lessor lessor = propertyImpl.getBailleurById(id);

        if (lessor != null) {
            return sendSuccess("Lessor id " + id, lessor);
        } else {
            return sendError(404, "Lessor non trouvé");
        }


    }

    /**
     * @param id          l'ID du bailleur à modifier
     * @param newLessor les nouvelles informations d'un bailleur
     * @return l'objet bailleur modifié en cas de succes et erreur en cas de probleme
     */
    @PUT
    @Path("lessor/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBailleur(@PathParam("id") Long id, Lessor newLessor) {
        Lessor lessor = propertyImpl.getBailleurById(id);
        if (lessor != null) {
            lessor.setAdresse(newLessor.getAdresse());
            lessor.setBailleurCategorie(newLessor.isBailleurCategorie());
            lessor.setBailleurNom(newLessor.getBailleurNom());
            lessor.setEmail(newLessor.getEmail());
            lessor.setNumeroPiece(newLessor.getNumeroPiece());
            lessor.setTelephone(newLessor.getTelephone());
            if (propertyImpl.updateBailleur(lessor)) {
                return sendSuccess("Lessor id " + id + " modifié avec succés!", lessor);
            }
            return sendError(200, "Erreur update");
        } else {
            return sendError(404, "Lessor non trouvé, impossible de faire une modification");
        }
    }


    /**
     * @return la liste des Picture
     */
    @GET
    @Path("/picture")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPhoto() {
        List<Picture> liste = propertyImpl.getAllPhoto();
        return sendSuccess("Liste des photos", liste);
    }


    /**
     *
     * @param id
     * @return
     */
    @GET
    @Path("/photo/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPhoto(@PathParam("id") Long id) {
        Picture picture = propertyImpl.getPhotoById(id);
        if(picture !=null){
            return sendSuccess("bien id " + id, picture);
        }else{
            return sendError(404, "picture non trouvé");
        }
    }


    /**
     *
     * @param fileInputStream
     * @param fileMetaData
     * @param bien_id int : identifiant du bien
     * @return
     * @throws Exception
     */
    @POST
    @Path("/picture/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public  Response addPhoto(@FormDataParam("file") InputStream fileInputStream, @FormDataParam("file") FormDataContentDisposition fileMetaData,@FormDataParam("bien") Long bien_id)throws Exception{
        Date date= new Date();

        long time = date.getTime();

        String FILENAME = time+"_";
        //reuperation de la classe courante et recupeation de la position de la classe enlever le dooser target ou out pour laisser la soutce projry
        String path = this.getClass().getResource("").getPath();
        int position_artifacts = path.indexOf("/artifacts");
        String phath2 = path.substring(0,position_artifacts);
        position_artifacts = phath2.lastIndexOf('/');
        path = phath2.substring(0,position_artifacts);
        path+="/images/";


        String   UPLOAD_PATH = path;

        try
        {
            int read = 0;
            byte[] bytes = new byte[1024];
            FILENAME += fileMetaData.getFileName();
            OutputStream out = new FileOutputStream(new File(UPLOAD_PATH + FILENAME));
            while ((read = fileInputStream.read(bytes)) != -1)
            {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e)
        {
            return sendError(500, ERROR_SERVER);
        }


        Property property = propertyImpl.getBienById(bien_id);
        if(property !=null){
            Picture picture = new Picture();
            picture.setPath(FILENAME);
            picture.setProperty(property);
            boolean save = propertyImpl.savePhoto(picture);
            if(save){
                return sendSuccess("picture enregistré  avec success!", picture);
            }else{
                return sendError(200, "picture non enregistré!");
            }
        }else {
            return sendError(200, "Property non trouver!");
        }
    }

    /**
     *
     * @param id identifiant le la photo
     * @return
     */
    @DELETE
    @Path("/picture/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletePhoto(@PathParam("id") Long id){

        Picture picture = propertyImpl.getPhotoById(id);

        if(picture !=null){
            try {

                boolean save = propertyImpl.deletePhoto(picture);
                if(save){
                    return sendSuccess("Property enregistré avec success!", picture);
                }else {
                    return sendError(200, "picture  non supprimer!");
                }
            }catch (Exception ex)
            {
                return sendError(500, ERROR_SERVER);
            }
        }else{
            return sendError(404, "Picture non trouvé, impossible de faire une suppression");
        }
    }



}
