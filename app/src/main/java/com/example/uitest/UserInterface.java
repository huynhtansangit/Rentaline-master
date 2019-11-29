package com.example.uitest;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserInterface {
    @GET("api/profiles")
    Call<ResponseBody> getInfoUser(@Header("Authorization") String authToken);

    @POST("api/profiles")
    Call<ResponseBody>  EditProfile(@Header("Authorization") String authToken, @Body HashMap<String,Object> body);
}
