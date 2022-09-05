package com.ikrima.task.githubuserapp.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ikrima.task.githubuserapp.ui.contents.maincontents.MainActivity
import com.ikrima.task.githubuserapp.data.retrofit.TokenAuthGithub
import com.ikrima.task.githubuserapp.databinding.ActivitySplashScreenBinding
import com.ikrima.task.githubuserapp.utils.sharedpreferences.Constant
import com.ikrima.task.githubuserapp.utils.sharedpreferences.PreferencesHelper

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = PreferencesHelper(this)

        // used when added personal access token github
        sharedPref.putValueString(Constant.prefTokenGithub, TokenAuthGithub.tokenGithubUser)

        showLogo()
    }


    private fun showLogo() {
        Handler(mainLooper).postDelayed(
            {
                moveToContent()
            }, splashDuration.toLong()
        )
    }

    private fun moveToContent() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }


    companion object {
        private const val splashDuration = 2000
    }

}