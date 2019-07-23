package main.java.com.m1gl.models;

import javax.persistence.Column;

/**
 * Classe ResponseModel
 * Cette classe est utilisé pour envoyer une reponse d'une demande au serveyr
 */

public class ResponseModel {


    private boolean success;

    private int code;

    @Column(nullable = false,length = 50)
    private String message;

    private Object data;

    public ResponseModel(boolean success, int code, String message, Object data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Object getData() {
        return data;
    }



    public void setCode(int code) {
        this.code = code;
    }



}
