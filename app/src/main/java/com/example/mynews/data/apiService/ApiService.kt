package com.example.mynews.data.apiService

import com.example.mynews.data.model.ApiResponse

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
     suspend fun getHeadLines(

         @Query("country") country: String="us",
         @Query("apikey") apikey: String="c386c2a63bfd41c3943ff3843a224419"
     ):ApiResponse


     @GET("everything")
     suspend fun getEveryThing(
         @Query("q") q: String="us",
         @Query("apikey") apikey: String="c386c2a63bfd41c3943ff3843a224419"
     ): ApiResponse
}