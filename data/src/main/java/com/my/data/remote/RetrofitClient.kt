package com.my.data.remote

import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(baseUrl: HttpUrl) {

    companion object {
        const val TAG = "RetrofitClient"
    }

    var apiInterface: ApiInterface

    init {
        val retrofit =
            Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        apiInterface = retrofit.create(ApiInterface::class.java)
    }

}