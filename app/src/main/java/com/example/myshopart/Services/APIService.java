package com.example.myshopart.Services;

public class APIService  {

    public static String baseURL = "https://myshopart.000webhostapp.com/";

    public static DataService getService(){
        return APIRetrofitClient.getClient(baseURL).create(DataService.class);
    }
}
