package com.application.artistsearch.service

import android.graphics.ColorSpace
import com.example.sanathitunes.models.Model
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/search")
    fun getResults(@Query("term") term: String
    ): Call<Model>
}