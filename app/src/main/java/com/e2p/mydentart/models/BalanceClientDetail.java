package com.e2p.mydentart.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BalanceClientDetail {

    @SerializedName("Date facturation")
    @Expose
    private String date_facturation;
    @SerializedName("Facturation")
    @Expose
    private Double facturation;
    @SerializedName("réglement")
    @Expose
    private Double reglement;
    @SerializedName("Référence")
    @Expose
    private String reference;
    @SerializedName("Solde")
    @Expose
    private Double solde;

    public BalanceClientDetail() {
    }

    public BalanceClientDetail(String date_facturation, Double facturation, Double reglement, String reference, Double solde) {
        this.date_facturation = date_facturation;
        this.facturation = facturation;
        this.reglement = reglement;
        this.reference = reference;
        this.solde = solde;
    }

    public String getDate_facturation() {
        return date_facturation;
    }

    public void setDate_facturation(String date_facturation) {
        this.date_facturation = date_facturation;
    }

    public Double getFacturation() {
        return facturation;
    }

    public void setFacturation(Double facturation) {
        this.facturation = facturation;
    }

    public Double getReglement() {
        return reglement;
    }

    public void setReglement(Double reglement) {
        this.reglement = reglement;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }


    public String getDate() {
        try {

            SimpleDateFormat _FromFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat _ToFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date _Date = _FromFormat.parse(this.date_facturation);
            return _ToFormat.format(_Date);
        } catch (ParseException e) {
            return "-";
        }
    }
}
