package com.my.data.remote

import com.my.domain.Album
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface{

    @GET("img/shared/technical-test.json")
    fun getAlbums(): Call<List<Album>>
}