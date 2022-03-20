package com.example.themovieapp.core

import java.lang.Exception

sealed class Resourse<out T> {
    //Primer estado de carga
    class Loading<out T>: Resourse<T>()
    //Segundo estado si regresa una consulta exitosa
    data class  Success<out T>(val data: T): Resourse<T>()
    //Tercer estado si ocurrio un error al consultar la información
    data class  Failure(val exception: Exception): Resourse<Nothing>()
}