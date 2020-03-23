package com.example.myshopart.Services;

public class APIService  {

    private static String baseURL = "https://myshopart.000webhostapp.com/Server/";

    public static DataService getService(){
        return APIRetrofitClient.getClient(baseURL).create(DataService.class);
    }
}
