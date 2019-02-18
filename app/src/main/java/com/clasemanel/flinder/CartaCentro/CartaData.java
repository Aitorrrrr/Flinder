package com.clasemanel.flinder.CartaCentro;

public class CartaData {

    String name, localidad;

    public CartaData(String name, String localidad)
    {
        this.name = name;
        this.localidad = localidad;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
}
