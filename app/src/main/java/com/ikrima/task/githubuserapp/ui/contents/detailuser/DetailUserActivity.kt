package com.ikrima.task.githubuserapp.ui.contents.detailuser

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikrima.task.githubuserapp.base.BaseActivityViewModel
import com.ikrima.task.githubuserapp.data.responses.DetailUserResponse
import com.ikrima.task.githubuserapp.data.responses.RepositoryResponses
import com.ikrima.task.githubuserapp.databinding.ActivityDetailUserBinding
import com.ikrima.task.githubuserapp.ui.contents.maincontents.GitHubUserViewModel
import com.ikrima.task.githubuserapp.utils.helper.ResultWrapper
import com.ikrima.task.githubuserapp.utils.helper.UIUtils.loadImage

class DetailUserActivity : BaseActivityViewModel<GitHubUserViewModel>() {

    private lateinit var binding : ActivityDetailUserBinding
    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        setViewModel = ViewModelProvider(this)[GitHubUserViewModel::class.java]

        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setParcelableData()

        settingViewModel()

        subscribeDetailUser()

        subscribeUserRepo()

    }


    private fun setParcelableData() {
        username = intent.getStringExtra("username").toString()
        binding.tvUsername.text = username
    }


    private fun settingViewModel() {
        viewModel.apply {
            setService(service)
            getDetailUser(username)
            getListUserRepo(username)
        }
    }


    private fun subscribeDetailUser() {
        binding.apply {
            viewModel.detailUser.observe(this@DetailUserActivity) {
                when(it) {
                    is ResultWrapper.Default -> {
                        // empty
                    }
                    is ResultWrapper.Empty -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@DetailUserActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                    is ResultWrapper.Failure -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@DetailUserActivity, it.title, Toast.LENGTH_SHORT).show()
                    }
                    is ResultWrapper.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is ResultWrapper.Success -> {
                        progressBar.visibility = View.GONE

                        val data = it.data as DetailUserResponse

                        civUser.loadImage(data.avatarURL, this@DetailUserActivity, progressBar)

                        tvName.text = data.name
                        tvUsername.text = data.username
                        tvBioUser.text = data.bio

                    }
                    else -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@DetailUserActivity, "Server dalam perbaikan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun subscribeUserRepo() {
        binding.apply {
            viewModel.listUserRepo.observe(this@DetailUserActivity) {
                when(it) {
                    is ResultWrapper.Default -> {
                        // empty
                    }
                    is ResultWrapper.Empty -> {
                        progressBarListRepo.visibility = View.GONE
                        Toast.makeText(this@DetailUserActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                    is ResultWrapper.Failure -> {
                        progressBarListRepo.visibility = View.GONE
                        Toast.makeText(this@DetailUserActivity, it.title, Toast.LENGTH_SHORT).show()
                    }
                    is ResultWrapper.Loading -> {
                        progressBarListRepo.visibility = View.VISIBLE
                    }
                    is ResultWrapper.Success -> {
                        progressBarListRepo.visibility = View.GONE

                        val data = it.data as List<RepositoryResponses>

                        settingRvUserRepo(data)

                    }
                    else -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@DetailUserActivity, "Server dalam perbaikan", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }


    private fun settingRvUserRepo(repository: List<RepositoryResponses>) {
        val rvUserRepoAdapter = RvUserRepositoryAdapter()
        binding.rvUserRepo.apply {
            layoutManager = LinearLayoutManager(this@DetailUserActivity)
            adapter = rvUserRepoAdapter
        }

        rvUserRepoAdapter.addListRepo(repository)
    }

}