package com.example.weather.helper

sealed class Resource<out T> {

    object Empty : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    object Initialize : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Failure(val message: String) : Resource<Nothing>()

}