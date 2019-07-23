package main.java.com.m1gl.controller;


import main.java.com.m1gl.models.Bailleur;
import main.java.com.m1gl.models.Bien;
import main.java.com.m1gl.models.Photo;
import main.java.com.m1gl.models.Typebien;
import main.java.com.m1gl.services.implementations.BienDAO;
import main.java.com.m1gl.utils.Utilitaire;
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

@Path("/biens")
public class BienControlleur extends BaseController {

    private final String ALL_FIELD_REQUIRE = "Renseigner tous les champs!";
    private final String ERROR_SERVER = "Erreur server";

    //    @EJB
    private final BienDAO ibiens = new BienDAO();


    /**
     * @return la liste des Biens
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllbiens() {
        List<Bien> liste = ibiens.getAllBiens();
        return sendSuccess("Liste des Biens", liste);
    }

    /**
     * @return la liste des types de biens
     */
    @GET
    @Path("/all-type-bien")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTypeBiens() {
        List<Typebien> liste = ibiens.allTypeBien();
        return sendSuccess("Liste types bien", liste);
    }

    /**
     * @return les infotmation  d'un Biens
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBiens(@PathParam("id") Long id) {
        Bien bien = ibiens.getBienById(id);

        if (bien != null) {
            return sendSuccess("bien id " + id, bien);
        } else {
            return sendError(404, "bien non trouvé");
        }
    }

//    /**
//     * @param bien le bien qu'on veut ajouter
//     * @return succes et le bien créé si tout est ok et error en cas de problème
//     * @apiNote formart de retour
//     * {
//     * "bailleur": {"id": 1},
//     * "bienNumero": "34092-4",
//     * "description": "maison2",
//     * "prixBailleur": 150000,
//     * "statut": false,
//     * "surface": 156.5,
//     * "typebien": {"id": 1}
//     * }
//     */
//    @POST
//    @Path("/add")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response addBien(Bien bien) {
//        if ( bien.getDescription().trim().equals("") || bien.getBienNumero().trim().equals("")) {
//            return sendError(200, ALL_FIELD_REQUIRE);
//        }
//
//        try {
//            boolean save = ibiens.saveBien(bien);
//            if (save) {
//                return sendSuccess("Bien enregistré avec success!", bien);
//            } else {
//                return sendError(200, "Bien non enregistré!");
//            }
//
//        } catch (Exception ex) {
//            return sendError(500, ERROR_SERVER);
//        }
//    }

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
        Bailleur bailleur1 = ibiens.getBailleurById(bailleur);
        Typebien typebien1 = ibiens.getTypeBienById(typebien);
        List<Photo> photos = new ArrayList<>();
        Utilitaire utilitaire = new Utilitaire();

        for (BodyPart part : body.getParent().getBodyParts()) {
            InputStream is = part.getEntityAs(InputStream.class);
            ContentDisposition meta = part.getContentDisposition();
            if (meta.getFileName() != null) {
                Photo photo = utilitaire.writeToFile(is, meta);
                photos.add(photo);
            }
        }
        try {
            Bien bien = new Bien();
            bien.setBienNumero(Utilitaire.generateNumBien());
            bien.setDescription(description);
            bien.setSurface(surface);
            bien.setStatut(true);
            bien.setBailleur(bailleur1);
            bien.setTypebien(typebien1);
            bien.setPrixBailleur(prix_bailleur);
            boolean save = ibiens.saveBien(bien);
            if (save) {
                if (!photos.isEmpty()) {
                    photos.forEach(x -> {
                        x.setBien(bien);
                        ibiens.savePhoto(x);
                    });
                    return sendSuccess("Bien et photo(s) enregistré avec success", bien);
                } else {
                    return sendSuccess("Bien enregistré avec success & error on upload picture!", bien);
                }
            } else {
                return sendError(200, "Bien non enregistré!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return sendError(500, ERROR_SERVER);
        }
    }

    @POST
    @Path("/add-bien-new")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addBienNew(@FormDataParam("photo") String photo,
                               @FormDataParam("description") String description, @FormDataParam("prix_bailleur") int prix_bailleur,
                               @FormDataParam("surface") Double surface, @FormDataParam("bailleur_id") Long bailleur,
                               @FormDataParam("typebien_id") Long typebien) throws IOException {
        if (description.trim().equals("")) {
            return sendError(200, ALL_FIELD_REQUIRE);
        }
        Bailleur bailleur1 = ibiens.getBailleurById(bailleur);
        Typebien typebien1 = ibiens.getTypeBienById(typebien);

        try {
            Bien bien = new Bien();
            bien.setBienNumero(Utilitaire.generateNumBien());
            bien.setDescription(description);
            bien.setSurface(surface);
            bien.setStatut(true);
            bien.setDateAjout(Timestamp.valueOf(LocalDateTime.now()));
            bien.setBailleur(bailleur1);
            bien.setTypebien(typebien1);
            bien.setPrixBailleur(prix_bailleur);
            boolean save = ibiens.saveBien(bien);
            if (save) {
                Photo photo1 = new Photo();
                photo1.setPath(photo);
                photo1.setBien(bien);
                boolean savePhoto = ibiens.savePhoto(photo1);
                if (savePhoto) {
                    return sendSuccess("Bien et photo(s) enregistré avec success", ibiens.getAllBiens());
                } else {
                    return sendSuccess("Bien enregistré avec success & error on upload picture!", ibiens.getAllBiens());
                }
            } else {
                return sendError(200, "Bien non enregistré!");
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
     * query
     * {
     * "bailleur": {
     * "id": 1
     * },
     * "bienNumero": "34092-4",
     * "description": "maison12",
     * "prixBailleur": 150000,
     * "statut": false,
     * "surface": 156.5,
     * "typebien": {
     * "id": 1
     * }
     * }
     */

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBien(@PathParam("id") Long id, Bien upbien) {
        Bien bienold = ibiens.getBienById(id);
        if (bienold != null) {
            bienold.setBienNumero(upbien.getBienNumero());
            bienold.setDescription(upbien.getDescription());
            bienold.setPrixBailleur(upbien.getPrixBailleur());
            bienold.setStatut(upbien.isStatut());
            bienold.setTypebien(upbien.getTypebien());
            bienold.setBailleur(upbien.getBailleur());

            if (ibiens.updateBien(bienold)) {
                return sendSuccess("Bien id " + id + " modifié avec succés!", upbien);
            }

            return sendError(200, "Erreur update");
        } else {
            return sendError(404, "Bien non trouvé, impossible de faire une modification");
        }

    }


    /**
     * @return la liste des bailleurs
     */
    @GET
    @Path("/bailleurs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBailleurs() {
        List<Bailleur> liste = ibiens.getAllBailleurs();
        return sendSuccess("Liste des Bailleurs", liste);
    }


    /**
     * @param bailleur le bailleur qu'on veut ajouter
     * @return succes et le bailleur créé si tout est ok et error en cas de problème
     */

    @POST
    @Path("/bailleurs")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBailleur(Bailleur bailleur) {
        bailleur.setBailleurCategorie(true);
        if (bailleur.getAdresse().trim().equals("") || bailleur.getEmail().trim().equals("") ||
                bailleur.getNumeroPiece().trim().equals("") || bailleur.getTelephone().trim().equals("") ||
                bailleur.getBailleurNom().trim().equals("")) {
            return sendError(200, ALL_FIELD_REQUIRE);
        } else {
            if (ibiens.getBailleurByTel(bailleur.getTelephone()) != null) {
                return sendError(200, "Ce numero de telephone est déjà associé à un bailleur");
            }

            if (ibiens.getBailleurByEmail(bailleur.getEmail()) != null) {
                return sendError(200, "Cette adresse email est déjà associé à un bailleur");
            }
        }

        try {
            boolean save = ibiens.saveBailleur(bailleur);

            if (save) {
                return sendSuccess("Bailleur enregistré avec success!", bailleur);
            } else {
                return sendError(200, "Bailleur non enregistré!");
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
    @Path("bailleurs/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getBailleurById(@PathParam("id") Long id) {

        Bailleur bailleur = ibiens.getBailleurById(id);

        if (bailleur != null) {
            return sendSuccess("Bailleur id " + id, bailleur);
        } else {
            return sendError(404, "Bailleur non trouvé");
        }


    }

    /**
     * @param id          l'ID du bailleur à modifier
     * @param newBailleur les nouvelles informations d'un bailleur
     * @return l'objet bailleur modifié en cas de succes et erreur en cas de probleme
     */
    @PUT
    @Path("bailleurs/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBailleur(@PathParam("id") Long id, Bailleur newBailleur) {
        Bailleur bailleur = ibiens.getBailleurById(id);
        if (bailleur != null) {
            bailleur.setAdresse(newBailleur.getAdresse());
            bailleur.setBailleurCategorie(newBailleur.isBailleurCategorie());
            bailleur.setBailleurNom(newBailleur.getBailleurNom());
            bailleur.setEmail(newBailleur.getEmail());
            bailleur.setNumeroPiece(newBailleur.getNumeroPiece());
            bailleur.setTelephone(newBailleur.getTelephone());
            if (ibiens.updateBailleur(bailleur)) {
                return sendSuccess("Bailleur id " + id + " modifié avec succés!", bailleur);
            }
            return sendError(200, "Erreur update");
        } else {
            return sendError(404, "Bailleur non trouvé, impossible de faire une modification");
        }
    }


    /**
     * @return la liste des Photo
     */
    @GET
    @Path("/photo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPhoto() {
        List<Photo> liste = ibiens.getAllPhoto();
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
        Photo  photo = ibiens.getPhotoById(id);
        if(photo!=null){
            return sendSuccess("bien id " + id, photo);
        }else{
            return sendError(404, "photo non trouvé");
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
    @Path("/photo/add")
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


        Bien bien = ibiens.getBienById(bien_id);
        if(bien!=null){
            Photo photo = new  Photo();
            photo.setPath(FILENAME);
            photo.setBien(bien);
            boolean save = ibiens.savePhoto(photo);
            if(save){
                return sendSuccess("photo enregistré  avec success!", photo);
            }else{
                return sendError(200, "photo non enregistré!");
            }
        }else {
            return sendError(200, "Bien non trouver!");
        }
    }

    /**
     *
     * @param id identifiant le la photo
     * @return
     */
    @DELETE
    @Path("/photo/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletePhoto(@PathParam("id") Long id){

        Photo photo = ibiens.getPhotoById(id);

        if(photo!=null){
            try {

                boolean save = ibiens.deletePhoto(photo);
                if(save){
                    return sendSuccess("Bien enregistré avec success!", photo);
                }else {
                    return sendError(200, "photo  non supprimer!");
                }
            }catch (Exception ex)
            {
                return sendError(500, ERROR_SERVER);
            }
        }else{
            return sendError(404, "Photo non trouvé, impossible de faire une suppression");
        }
    }



}
