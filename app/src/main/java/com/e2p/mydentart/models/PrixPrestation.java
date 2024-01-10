package com.e2p.mydentart.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrixPrestation {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Prix")
    @Expose
    private Double prix;

    public PrixPrestation() {
    }

    public PrixPrestation(String name, Double prix) {
        this.name = name;
        this.prix = prix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }
}
