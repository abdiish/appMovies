package com.example.themovieapp.repository

import com.example.themovieapp.data.model.MovieList

//Definir metodos que se usaran en el data source

//Corutinas, uso de suspend

interface MovieRepository {
    suspend fun getUpcomingMovies(): MovieList
    suspend fun getTopRatedMovies(): MovieList
    suspend fun getPopularMovies() : MovieList
}