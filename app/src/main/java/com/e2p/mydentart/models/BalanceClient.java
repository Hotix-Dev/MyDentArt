package com.e2p.mydentart.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BalanceClient {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Facturation")
    @Expose
    private Double facturation;
    @SerializedName("Réglement")
    @Expose
    private Double reglement;
    @SerializedName("Balance")
    @Expose
    private Double balance;

    public BalanceClient() {
    }

    public BalanceClient(Integer id, String code, String name, Double facturation, Double reglement, Double balance) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.facturation = facturation;
        this.reglement = reglement;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
