package com.my.data.remote

import retrofit2.http.GET

interface ApiInterface {

    @GET("img/shared/technical-test.json")
    suspend fun getAlbums(): List<NetworkAlbum>
}