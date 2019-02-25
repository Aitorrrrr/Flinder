package com.clasemanel.flinder.Chat;

public class ChatMessage {

    private String idMensaje;
    private String idEmisor;
    private String msg;
    private boolean esMatch;

    public ChatMessage(String idMensaje, String idEmisor, String msg, boolean esMatch) {
        this.idEmisor = idEmisor;
        this.msg = msg;
        this.idMensaje = idMensaje;
        this.esMatch = esMatch;
    }

    public String getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(String idEmisor) {
        this.idEmisor = idEmisor;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }

    public boolean isEsMatch() {
        return esMatch;
    }

    public void setEsMatch(boolean esMatch) {
        this.esMatch = esMatch;
    }
}
