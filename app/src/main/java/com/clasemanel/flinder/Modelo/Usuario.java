package com.clasemanel.flinder.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {

    String nombreUsuario;
    String apellidos;
    String email;
    String pass;
    String localidad;
    String nombre;
    String fechaDeNacimiento;
    String genero;
    String preferenciasSexuales;

    public Usuario()
    {

    }

    public Usuario(String nombreUsuario, String apellidos, String email, String pass, String localidad, String nombre, String fechaDeNacimiento, String genero, String preferenciasSexuales) {
        this.nombreUsuario = nombreUsuario;
        this.apellidos = apellidos;
        this.email = email;
        this.pass = pass;
        this.localidad = localidad;
        this.nombre = nombre;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.genero = genero;
        this.preferenciasSexuales = preferenciasSexuales;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPreferenciasSexuales() {
        return preferenciasSexuales;
    }

    public void setPreferenciasSexuales(String preferenciasSexuales) {
        this.preferenciasSexuales = preferenciasSexuales;
    }

    protected Usuario(Parcel in) {
        nombreUsuario = in.readString();
        apellidos = in.readString();
        email = in.readString();
        pass = in.readString();
        localidad = in.readString();
        nombre = in.readString();
        fechaDeNacimiento = in.readString();
        genero = in.readString();
        preferenciasSexuales = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombreUsuario);
        dest.writeString(apellidos);
        dest.writeString(email);
        dest.writeString(pass);
        dest.writeString(localidad);
        dest.writeString(nombre);
        dest.writeString(fechaDeNacimiento);
        dest.writeString(genero);
        dest.writeString(preferenciasSexuales);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
}
