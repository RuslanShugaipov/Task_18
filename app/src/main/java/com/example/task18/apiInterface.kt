package com.example.task18

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface apiInterface {
    @GET("posts")
    fun getData(): Call<List<MsgItem>>

    @FormUrlEncoded
    @POST("post")
    fun updateData(
        @Field("body") body: String,
        @Field("id") id: Int,
        @Field("title") title: String,
        @Field("userId") userId: Int
    ): Call<MsgItem>
}