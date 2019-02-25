package com.clasemanel.flinder.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class ChatPicker implements Parcelable {

    private String nombreUser;
    private String idUser;
    private String imagen;
    private String idChat;
    private boolean imagenCargada;
    private String idMatch;

    public ChatPicker() {

    }

    public ChatPicker(String idUser) {
        this.idUser = idUser;
    }

    public ChatPicker(String idUser, String idChat) {
        this.idChat = idChat;
        this.idUser = idUser;
    }

    protected ChatPicker(Parcel in) {
        nombreUser = in.readString();
        idUser = in.readString();
        imagen = in.readString();
        idChat = in.readString();
        imagenCargada = in.readByte() != 0x00;
        idMatch = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombreUser);
        dest.writeString(idUser);
        dest.writeString(imagen);
        dest.writeString(idChat);
        dest.writeByte((byte) (imagenCargada ? 0x01 : 0x00));
        dest.writeString(idMatch);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ChatPicker> CREATOR = new Parcelable.Creator<ChatPicker>() {
        @Override
        public ChatPicker createFromParcel(Parcel in) {
            return new ChatPicker(in);
        }

        @Override
        public ChatPicker[] newArray(int size) {
            return new ChatPicker[size];
        }
    };

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String ultimMensaje) {
        this.idUser = idUser;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public boolean isImagenCargada() {
        return imagenCargada;
    }

    public void setImagenCargada(boolean imagenCargada) {
        this.imagenCargada = imagenCargada;
    }

    public String getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(String idMatch) {
        this.idMatch = idMatch;
    }
}