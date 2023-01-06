package com.example.touris.Sever;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Dataservice {


    @FormUrlEncoded
    @POST("login.php")
    Call<responsemodel>verifyUser(@Field("email") String email,
                                  @Field("password") String password);
    @FormUrlEncoded
    @POST("register.php")
    Call<responsemodel> PostUser(@Field("username") String username,
                        @Field("password") String password,
                        @Field("email") String email);
}
