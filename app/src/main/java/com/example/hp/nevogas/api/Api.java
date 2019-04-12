package com.example.hp.nevogas.api;

import com.example.hp.nevogas.model.AgencyLoginResponse;
import com.example.hp.nevogas.model.AgencyRegisterResponse;
import com.example.hp.nevogas.model.DefaultResponse;
import com.example.hp.nevogas.model.LoginResponse;
import com.example.hp.nevogas.model.OrderedGas;
import com.example.hp.nevogas.model.ShowOrderGas;
import com.example.hp.nevogas.model.Station;
import com.example.hp.nevogas.model.UserOrderResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by HP on 3/14/2019.
 */

public interface Api {

    @FormUrlEncoded
    @POST("user/register")
    Call<DefaultResponse> CreateUser(
            @Field("name")  String name,
            @Field("email") String email,
            @Field("state") String state,
            @Field("phone") String phone,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST("user/login")
    Call<LoginResponse> LoginUser(
            @Field("email")  String email,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST("user/googleupdate")
    Call<LoginResponse> UpdateUser(
            @Field("id") int id,
            @Field("address")  String address,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude

    );

    @FormUrlEncoded
    @POST("user/gas_station")
    Call<List<Station>> SearchGas(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("user/place_orders")
    Call<UserOrderResponse> UserOrder(
            @Field("qty") String qty,
            @Field("price") String price,
            @Field("user_id") String user_id,
            @Field("agency_id") String agency_id

    );
    @FormUrlEncoded
    @POST("user/show_order")
    Call<List<ShowOrderGas>> UserHistory(
            @Field("user_id") int user_id

    );


    /* Agency API CALL*/
    @FormUrlEncoded
    @POST("agency/register")
    Call<AgencyRegisterResponse> CreateAgency(
            @Field("business_name") String business_name,
            @Field("email")  String email,
            @Field("state") String state,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("about_us") String about_us

    );

    @FormUrlEncoded
    @POST("agency/login")
    Call<AgencyLoginResponse> LoginAgency(
            @Field("email")  String email,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST("agency/googleupdate")
    Call<AgencyLoginResponse> UpdateLocation(
            @Field("id") int id,
            @Field("address")  String address,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude

    );

    @FormUrlEncoded
    @POST("agency/show_orders")
    Call<List<ShowOrderGas>> ShowOrder(
            @Field("agency_id") int agency_id
    );


}
