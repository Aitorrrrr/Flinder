package com.clasemanel.flinder.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Imagenes implements Parcelable {

    private ArrayList<String> imagenes;

    public Imagenes()
    {
        this.imagenes = new ArrayList<String>();
    }

    protected Imagenes(Parcel in) {
        if (in.readByte() == 0x01) {
            imagenes = new ArrayList<String>();
            in.readList(imagenes, String.class.getClassLoader());
        } else {
            imagenes = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (imagenes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(imagenes);
        }
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

    public void anyadirImagen(String imagen)
    {
        imagenes.add(imagen);
    }

    public String recuperarImagen(int posicion)
    {
        return imagenes.get(posicion);
    }

    public int getSize()
    {
        return this.imagenes.size();
    }
}