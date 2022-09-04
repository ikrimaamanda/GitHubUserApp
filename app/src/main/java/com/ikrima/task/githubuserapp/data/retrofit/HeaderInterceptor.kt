package com.ikrima.task.githubuserapp.data.retrofit

import android.content.Context
import com.ikrima.task.githubuserapp.utils.sharedpreferences.Constant
import com.ikrima.task.githubuserapp.utils.sharedpreferences.PreferencesHelper
import okhttp3.Interceptor
import okhttp3.Response


class HeaderInterceptor(private val context : Context) : Interceptor {

    private lateinit var sharedPref : PreferencesHelper

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        sharedPref = PreferencesHelper(context)

        val tokenAuth = sharedPref.getValueString(Constant.prefTokenGithub)
        proceed(
            request().newBuilder()
                .addHeader("Authorization", "token $tokenAuth")
                .header("Connection", "close")
                .removeHeader("Content-Length")
                .build()
        )
    }

}