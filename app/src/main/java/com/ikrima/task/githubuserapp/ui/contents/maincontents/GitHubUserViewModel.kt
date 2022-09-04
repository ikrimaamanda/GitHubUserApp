package com.ikrima.task.githubuserapp.ui.contents.maincontents

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikrima.task.githubuserapp.data.responses.DetailUserResponse
import com.ikrima.task.githubuserapp.data.responses.RepositoryResponses
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

    private val _detailUser = MutableLiveData<ResultWrapper<Any>>()
    val detailUser : LiveData<ResultWrapper<Any>> = _detailUser

    private val _listUserRepo = MutableLiveData<ResultWrapper<List<Any>>>()
    val listUserRepo : LiveData<ResultWrapper<List<Any>>> = _listUserRepo

    init {
        _listSearchUser.value = ResultWrapper.default()
        _detailUser.value = ResultWrapper.default()
        _listUserRepo.value = ResultWrapper.default()
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


    fun getDetailUser(username : String) {
        _detailUser.value = ResultWrapper.loading()
        launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    service.getDetailUser(username)
                }

                result.enqueue(object : Callback<DetailUserResponse> {
                    override fun onResponse(
                        call: Call<DetailUserResponse>,
                        response: Response<DetailUserResponse>
                    ) {
                        if (response.isSuccessful) {
                            if (response.code() == HttpURLConnection.HTTP_OK) {
                                response.body().let {
                                    if (it != null) {
                                        _detailUser.value = ResultWrapper.success(it)
                                    } else {
                                        _detailUser.value = ResultWrapper.fail(title = "Server dalam perbaikan", "")
                                    }
                                }
                            }

                            Log.d("successGetDetailUser", response.body().toString())
                        } else {
                            Log.e("failedGetDetailUser", response.toString())
                            when(response.code()) {
                                404 -> {
                                    _detailUser.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                }
                                else -> {
                                    _detailUser.value = ResultWrapper.fail(title = "Server dalam perbaikan", "")
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                        Log.e("failureGetDetailUser", "onResponse : ${t.localizedMessage}")
                        _detailUser.value = ResultWrapper.fail(title = "Gagal mendapatkan data", "")
                    }

                })

            } catch (e : Throwable) {
                Log.e("errorGetDetailUser", "Msg : ${e.localizedMessage}")
            }
        }
    }

    fun getListUserRepo(username : String) {
        _listUserRepo.value = ResultWrapper.loading()
        launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    service.getListUserRepo(username)
                }

                result.enqueue(object : Callback<List<RepositoryResponses>> {
                    override fun onResponse(
                        call: Call<List<RepositoryResponses>>,
                        response: Response<List<RepositoryResponses>>
                    ) {
                        if (response.isSuccessful) {
                            if (response.code() == HttpURLConnection.HTTP_OK) {
                                response.body().let {
                                    if (it != null) {
                                        _listUserRepo.value = ResultWrapper.success(it)
                                    } else {
                                        _listUserRepo.value = ResultWrapper.fail(title = "Server dalam perbaikan", "")
                                    }
                                }
                            }

                            Log.d("successGetListUserRepo", response.body().toString())
                        } else {
                            Log.e("failedGetListUserRepo", response.toString())
                            when(response.code()) {
                                404 -> {
                                    _listUserRepo.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                }
                                else -> {
                                    _listUserRepo.value = ResultWrapper.fail(title = "Server dalam perbaikan", "")
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<RepositoryResponses>>, t: Throwable) {
                        Log.e("failureGetListUserRepo", "onResponse : ${t.localizedMessage}")
                        _listUserRepo.value = ResultWrapper.fail(title = "Gagal mendapatkan data", "")
                    }

                })

            } catch (e : Throwable) {
                Log.e("errorGetListUserRepo", "Msg : ${e.localizedMessage}")
            }
        }
    }


    /*
    * This method to cancel coroutine when activity/fragment is destroyed
    * */
    override fun onCleared() {
        Job().cancel()
        super.onCleared()
    }

}