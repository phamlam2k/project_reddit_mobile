package com.example.userinterface.api;

import com.example.userinterface.post.Post;
import com.example.userinterface.user.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://61440844411c860017d25289.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("user")
    Call<List<User>> getListUsers ();

    @GET("port")
    Call<List<Post>> getListPosts ();

    @FormUrlEncoded
    @POST("user")
    Call<User> postListUsers (
            @Field("username") String username,
            @Field("password") String password
    );




}
