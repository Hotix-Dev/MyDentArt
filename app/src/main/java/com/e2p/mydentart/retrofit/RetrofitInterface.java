package com.e2p.mydentart.retrofit;

import com.e2p.mydentart.models.BalanceClient;
import com.e2p.mydentart.models.BalanceClientDetail;
import com.e2p.mydentart.models.PrixPrestation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitInterface {

    /***
     ** GET *********************************************************************************************
     **/

    //Get Clients Balance service call
    @GET
    Call<ArrayList<BalanceClient>> getClientsBalanceQuery(@Url String URL);

    //Get Client Balance Details service call
    @GET
    Call<ArrayList<BalanceClientDetail>> getClientBalanceDetailsQuery(@Url String URL,
                                                                      @Query("clientId") Integer clientId);

    //Get Prix Prestations service call
    @GET
    Call<ArrayList<PrixPrestation>> getPrixPrestationsQuery(@Url String URL);

    /***
     ** POST ********************************************************************************************
     **/

}
