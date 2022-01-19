package com.agaperra.filmslight.di

import android.content.Context
import com.agaperra.filmslight.data.api.FilmsApi
import com.agaperra.filmslight.utils.Constants.FILM_BASIC_URL
import com.agaperra.filmslight.utils.network.NetworkStatusListener
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideApiService(client: OkHttpClient): FilmsApi = Retrofit.Builder()
        .client(client)
        .baseUrl(FILM_BASIC_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
        .build()
        .create(FilmsApi::class.java)


    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideNetworkStatusListener(@ApplicationContext context: Context): NetworkStatusListener =
        NetworkStatusListener(context)

}