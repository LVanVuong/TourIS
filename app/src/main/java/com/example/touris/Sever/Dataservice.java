package com.example.touris.Sever;

import com.example.touris.Login.LoginRequest;
import com.example.touris.Login.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Dataservice {

    @GET("user.php")
    Call<User> GetUsername(@Body LoginRequest loginRequest);
}
