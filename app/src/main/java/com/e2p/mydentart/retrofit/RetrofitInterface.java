package com.e2p.mydentart.retrofit;

import com.e2p.mydentart.models.Client;
import com.e2p.mydentart.models.Statement;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitInterface {

    /***
     ** GET *********************************************************************************************
     **/

    //Get All Clients service call
    @GET
    Call<ArrayList<Client>> getAllClientsQuery(@Url String URL);

    //Get Client By Code service call
    @GET
    Call<Client> getClientByCodeQuery(@Url String URL,
                                                  @Query("Id") Integer Id,
                                                  @Query("code") String code);

    //Get All Statements service call
    @GET
    Call<ArrayList<Statement>> getAllStatementsQuery(@Url String URL,
                                                      @Query("cabinetId") Integer cabinetId,
                                                      @Query("clientId") Integer clientId,
                                                      @Query("exerciceDebut") Integer exerciceDebut,
                                                      @Query("exerciceFin") Integer exerciceFin);

    /***
     ** POST ********************************************************************************************
     **/

}
