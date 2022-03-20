package com.example.themovieapp.repository

import com.example.themovieapp.aplication.AppConstants
import com.example.themovieapp.data.model.MovieList
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//Encargado de usar retrofit para traer la información del servidor

interface WebService {
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") api_key: String): MovieList
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") api_key: String): MovieList
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") api_key: String) : MovieList
}

object RetrofitClient {

    val webService by lazy {
        Retrofit.Builder().baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}