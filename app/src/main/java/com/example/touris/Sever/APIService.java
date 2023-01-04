package com.example.touris.Sever;

public class APIService {
    private static String base_url = "https://usertour.000webhostapp.com/sever";

    public static Dataservice getService(){
        return APIRetrofitClient.getClient(base_url).create(Dataservice.class);
    }
}
