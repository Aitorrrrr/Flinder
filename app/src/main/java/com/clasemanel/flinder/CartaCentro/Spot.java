package com.clasemanel.flinder.CartaCentro;

public class Spot {
    public String id;
    public String name;
    public String city;
    public String url;

    public Spot()
    {

    }

    public Spot(String id, String name, String city, String url) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}