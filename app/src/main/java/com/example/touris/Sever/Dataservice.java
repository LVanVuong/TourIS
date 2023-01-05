package com.example.touris.Sever;

import com.android.volley.Request;
import com.example.touris.Login.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Dataservice {

    @GET("user.php")
    Call<List<User>> GetUsername();
    @POST("register.php")
    Request<List<User>> PostUser();
}
