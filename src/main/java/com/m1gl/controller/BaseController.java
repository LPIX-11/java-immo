package main.java.com.m1gl.controller;


import main.java.com.m1gl.models.ResponseModel;

import javax.ws.rs.core.Response;



/**
 * BaseController superclasse des controller
 * Elle a deux methodes sendSuccess et sendError permettant de renvoyer
 * les donnees en cas de succes ou de renvoyer l'erreur en cas error
 */
public class BaseController {


    /**
     *
     * @param message le message de success qu'on veut envoyer au front
     * @param data c'est la donnée (Objet, liste, string etc.)
     * @return une réponse de type javax.ws.rs.core.Response créer à partir du model ResponseModel
     */
    public static Response sendSuccess(String message,Object data){

        return Response.ok().entity(new ResponseModel(true,200,message,data)).build();

    }

    /**
     *
     * @param message le message d'erreur qu'on veut envoyer au front
     * @param code c'est le code d'erreur
     * @return une réponse de type javax.ws.rs.core.Response créer à partir du model ResponseModel
     */
    public static Response sendError(int code,String message){

        return Response.status(code).entity(new ResponseModel(false,code,message,null)).build();

    }
}
