package com.ikrima.task.githubuserapp.ui.contents.maincontents

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikrima.task.githubuserapp.data.responses.SearchUserResponse
import com.ikrima.task.githubuserapp.data.retrofit.GitHubUserApiServices
import com.ikrima.task.githubuserapp.utils.helper.ResultWrapper
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection
import kotlin.coroutines.CoroutineContext


class GitHubUserViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var service : GitHubUserApiServices

    private val _listSearchUser = MutableLiveData<ResultWrapper<List<Any>>>()
    val listSearchUser : LiveData<ResultWrapper<List<Any>>> = _listSearchUser

    init {
        _listSearchUser.value = ResultWrapper.default()
    }

    fun setService(service : GitHubUserApiServices) {
        this.service = service
    }


    fun searchUser(username : String) {
        _listSearchUser.value = ResultWrapper.loading()
        launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    service.searchUser(username)
                }

                result.enqueue(object : Callback<SearchUserResponse> {
                    override fun onResponse(
                        call: Call<SearchUserResponse>,
                        response: Response<SearchUserResponse>
                    ) {
                        if (response.isSuccessful) {
                            if (response.code() == HttpURLConnection.HTTP_OK) {
                                response.body().let {
                                    if (it != null) {
                                        _listSearchUser.value = ResultWrapper.success(it.items)
                                    } else {
                                        _listSearchUser.value = ResultWrapper.fail(title = "Server dalam perbaikan", "")
                                    }
                                }
                            }

                            Log.d("successSearchUser", response.body().toString())
                        } else {
                            Log.e("failedSearchUser", response.toString())
                            when(response.code()) {
                                404 -> {
                                    _listSearchUser.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                }
                                else -> {
                                    _listSearchUser.value = ResultWrapper.fail(title = "Server dalam perbaikan", "")
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                        Log.e("failureSearchUser", "onResponse : ${t.localizedMessage}")
                        _listSearchUser.value = ResultWrapper.fail(title = "Gagal mendapatkan data", "")
                    }

                })

            } catch (e : Throwable) {
                Log.e("errorSearchUser", "Msg : ${e.localizedMessage}")
            }
        }
    }

}