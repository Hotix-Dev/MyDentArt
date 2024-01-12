package com.e2p.mydentart.helpers;

import com.e2p.mydentart.models.BalanceClient;
import com.e2p.mydentart.models.Client;
import com.e2p.mydentart.models.PrixPrestation;

import java.util.ArrayList;

public class ConstantConfig {
    /********************** *****************( Final )************************  *******************/

    //FINAL App Id
    public static final String FINAL_APP_ID = "2";

    /***************************************(Non Final )*******************************************/
    //BASE URL
    public static String BASE_URL = "http://41.228.164.76:92/MyDentArtAPI/";

    public static ArrayList<BalanceClient> ALL_CLIENTS_BALANCES = null;
    public static ArrayList<PrixPrestation> ALL_PRIX_PRESTATIONS = null;
    public static ArrayList<PrixPrestation>  SELECTED_PRIX_PRESTATIONS = null;
    public static ArrayList<Client> ALL_CLIENTS = null;
    public static Client CURENT_CLIENT = null;
    public static Client  SELECTED_CLIENT = null;

    public static String  AB_TITLE = "";

}

