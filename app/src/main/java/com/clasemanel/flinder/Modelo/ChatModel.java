package com.clasemanel.flinder.Modelo;

public class ChatModel {

    private String creadoPor;
    private String mensaje;

    public ChatModel(String creadoPor, String mensaje) {
        this.creadoPor = creadoPor;
        this.mensaje = mensaje;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
