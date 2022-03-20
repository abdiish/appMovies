package com.example.themovieapp.data.remote

import com.example.themovieapp.aplication.AppConstants
import com.example.themovieapp.data.model.MovieList
import com.example.themovieapp.repository.WebService

//Capa de datos

class MovieDataSource(private val webService: WebService) {

    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovies(AppConstants.API_KEY)

    suspend fun getTopRatedMovies(): MovieList = webService.getTopRatedMovies(AppConstants.API_KEY)

    suspend fun getPopularMovies(): MovieList = webService.getPopularMovies(AppConstants.API_KEY)
}