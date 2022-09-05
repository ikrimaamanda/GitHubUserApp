package com.ikrima.task.githubuserapp.data.retrofit

import android.content.Context
import com.ikrima.task.githubuserapp.BuildConfig
import com.ikrima.task.githubuserapp.data.remote.UrlEndPoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {

    private var retrofitAPIGitHubUser : Retrofit? = null

    private fun providenHttpLoggingInterceptor() = run {
        HttpLoggingInterceptor().apply {

            apply { level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else HttpLoggingInterceptor.Level.NONE

            }
        }
    }

    fun getApiClientGitHubUser(context : Context) : Retrofit? {
        if (retrofitAPIGitHubUser == null) {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(providenHttpLoggingInterceptor())
                    // used when added personal access token github
//                .addInterceptor(HeaderInterceptor(context))
                .connectTimeout(7000, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .build()

            retrofitAPIGitHubUser = Retrofit.Builder()
                .baseUrl(UrlEndPoint.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitAPIGitHubUser
    }
}