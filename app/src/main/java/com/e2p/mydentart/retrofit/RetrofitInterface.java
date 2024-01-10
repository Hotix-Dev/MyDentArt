package com.e2p.mydentart.retrofit;

import com.e2p.mydentart.models.BalanceClient;
import com.e2p.mydentart.models.PrixPrestation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitInterface {

    /***
     ** GET *********************************************************************************************
     **/

    //Get All Clients service call
    @GET
    Call<ArrayList<BalanceClient>> getClientsBalanceQuery(@Url String URL);

    //Get All Clients service call
    @GET
    Call<ArrayList<PrixPrestation>> getPrixPrestationsQuery(@Url String URL);

    /***
     ** POST ********************************************************************************************
     **/

}
