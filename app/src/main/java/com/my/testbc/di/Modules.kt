package com.my.testbc.di

import androidx.room.Room
import com.my.data.Repository
import com.my.data.local.AppDatabase
import com.my.data.remote.ApiInterface
import com.my.testbc.AlbumsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val localModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java, "albums.db"
        ).build()
    }
    single { get<AppDatabase>().dataDao() }
}

val repositoryModule = module {
    single { Repository(get(), get()) }
}

val viewModelModule = module {
    viewModel { AlbumsViewModel(repository = get()) }
}

val remoteModule = module {
    single(override = true) {
        Retrofit.Builder()
            .baseUrl("https://static.leboncoin.fr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { provideApiInterface(get()) }
}

fun provideApiInterface(retrofit: Retrofit): ApiInterface =
    retrofit.create(ApiInterface::class.java)
