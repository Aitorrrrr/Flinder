package com.clasemanel.flinder.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class Imagenes implements Parcelable {

    private String imagen1;
    private String imagen2;
    private String imagen3;
    private String imagen4;
    private String imagen5;
    private String imagen6;

    public Imagenes(){

    }
    public Imagenes(String imagen1, String imagen2, String imagen3, String imagen4, String imagen5, String imagen6) {
        this.imagen1 = imagen1;
        this.imagen2 = imagen2;
        this.imagen3 = imagen3;
        this.imagen4 = imagen4;
        this.imagen5 = imagen5;
        this.imagen6 = imagen6;
    }

    public String getImagen1() {
        return imagen1;
    }

    public void setImagen1(String imagen1) {
        this.imagen1 = imagen1;
    }

    public String getImagen2() {
        return imagen2;
    }

    public void setImagen2(String imagen2) {
        this.imagen2 = imagen2;
    }

    public String getImagen3() {
        return imagen3;
    }

    public void setImagen3(String imagen3) {
        this.imagen3 = imagen3;
    }

    public String getImagen4() {
        return imagen4;
    }

    public void setImagen4(String imagen4) {
        this.imagen4 = imagen4;
    }

    public String getImagen5() {
        return imagen5;
    }

    public void setImagen5(String imagen5) {
        this.imagen5 = imagen5;
    }

    public String getImagen6() {
        return imagen6;
    }

    public void setImagen6(String imagen6) {
        this.imagen6 = imagen6;
    }

    protected Imagenes(Parcel in) {
        imagen1 = in.readString();
        imagen2 = in.readString();
        imagen3 = in.readString();
        imagen4 = in.readString();
        imagen5 = in.readString();
        imagen6 = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imagen1);
        dest.writeString(imagen2);
        dest.writeString(imagen3);
        dest.writeString(imagen4);
        dest.writeString(imagen5);
        dest.writeString(imagen6);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Imagenes> CREATOR = new Parcelable.Creator<Imagenes>() {
        @Override
        public Imagenes createFromParcel(Parcel in) {
            return new Imagenes(in);
        }

        @Override
        public Imagenes[] newArray(int size) {
            return new Imagenes[size];
        }
    };
}
