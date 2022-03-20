package com.example.themovieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.themovieapp.core.Resourse
import com.example.themovieapp.repository.MovieRepository
import kotlinx.coroutines.Dispatchers

//Capa de datos
class MovieViewModel(private val repo: MovieRepository): ViewModel() {

    fun fetchMainScreenMovies() = liveData(Dispatchers.IO){
        //Estado de carga
        emit(Resourse.Loading())

        //Llamada al servidor
        try {
            //Succcess
            //Triple
            emit(Resourse.Success(Triple(repo.getUpcomingMovies(), repo.getTopRatedMovies(), repo.getPopularMovies())))
        } catch (e: Exception) {
            //Failure
            emit(Resourse.Failure(e))
        }
    }

}

class MovieViewModelFactory(private val repo: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repo)
    }
}
