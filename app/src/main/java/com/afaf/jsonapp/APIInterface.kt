package com.afaf.jsonapp

import com.afaf.jsonapp.currencies
import retrofit2.Call
import retrofit2.http.GET


interface APIInterface {

    @GET("eur.json")
    fun getCurrency(): Call<currencies>?
}