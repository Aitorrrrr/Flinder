package com.clasemanel.flinder.Modelo;

public class ChatPicker {

    private String nombreUser;
    private String id;
    private String imagen;

    public ChatPicker() {

    }

    public ChatPicker(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public ChatPicker(String nombreUser, String id) {
        this.nombreUser = nombreUser;
        this.id = id;
    }

    public ChatPicker(String nombreUser, String id, String imagen) {
        this.nombreUser = nombreUser;
        this.id = id;
        this.imagen=imagen;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String ultimMensaje) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
