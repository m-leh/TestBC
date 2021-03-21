package com.my.data.remote

import android.util.Log
import com.my.domain.Album
import okhttp3.HttpUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(baseUrl: HttpUrl, private var onAlbumsChange: (List<Album>) -> Unit) {

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

    fun loadData() {
        val call = apiInterface.getAlbums()
        call.enqueue(object : Callback<List<Album>> {
            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                if (!response.isSuccessful) {
                    Log.e(TAG, "onResponse: response albums failed")
                    return
                }
                val albumList = response.body()
                if (albumList != null) {
                    onAlbumsChange(albumList)
                }
            }

            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                Log.e(TAG, "onFailure " + t.message)
            }
        })
    }

}