package com.example.pavel.village.Remote;

import com.example.pavel.village.Model.ApiResponse;
import com.example.pavel.village.Model.Product;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IMyAPI {
    @FormUrlEncoded
    @POST("login.php")
    Call<ApiResponse> loginUser(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<ApiResponse> registerUser(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("read.php")
    Call<ApiResponse> detailUser(@Field("id") String id);

    @FormUrlEncoded
    @POST("edit.php")
    Call<ApiResponse> editDetailUser(@Field("name") String name,@Field("email") String email,@Field("id") String id);

    @FormUrlEncoded
    @POST("upload.php")
    Call<ApiResponse> uploadPhoto(@Field("id") String id,@Field("photo") String photo);

    @FormUrlEncoded
    @POST("offer.php")
    Call<ApiResponse> offerProduct(@Field("user_id") int user_id,@Field("name_product") String name,@Field("description") String description
            ,@Field("price") int price,@Field("date_created") String date_created, @Field("photo_product") String photo_product);

    @FormUrlEncoded
    @POST("uploadProduct.php")
    Call<ApiResponse> photoProduct(@Field("photo_product") String photo_product);


    @GET("find.php")
    Call<List<Product>> getProducts(@Query("name") String name);


}
