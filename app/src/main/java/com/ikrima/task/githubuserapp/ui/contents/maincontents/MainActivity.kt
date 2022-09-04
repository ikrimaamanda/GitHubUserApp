package com.ikrima.task.githubuserapp.ui.contents.maincontents

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikrima.task.githubuserapp.R
import com.ikrima.task.githubuserapp.base.BaseActivityViewModel
import com.ikrima.task.githubuserapp.data.responses.DetailUserResponse
import com.ikrima.task.githubuserapp.databinding.ActivityMainBinding
import com.ikrima.task.githubuserapp.utils.helper.ResultWrapper

class MainActivity : BaseActivityViewModel<GitHubUserViewModel>() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setViewModel = ViewModelProvider(this)[GitHubUserViewModel::class.java]

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingViewModel()

        settingSearchUser()

        subscribeSearchUser()
    }


    private fun settingViewModel() {
        viewModel.apply {
            setService(service)
        }
    }


    private fun settingSearchUser() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        binding.searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = resources.getString(R.string.search_by_username)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        binding.emptyLayout.visibility = View.GONE
                        viewModel.searchUser(query)
                    } else {
                        Toast.makeText(this@MainActivity, "Pencarian null", Toast.LENGTH_SHORT).show()
                    }
                    binding.searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }

    }


    private fun subscribeSearchUser() {
        binding.apply {
            viewModel.listSearchUser.observe(this@MainActivity) {
                when(it) {
                    is ResultWrapper.Default -> {

                    }
                    is ResultWrapper.Empty -> {
                        progressBar.visibility = View.GONE

                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                    is ResultWrapper.Failure -> {
                        progressBar.visibility = View.GONE

                        Toast.makeText(this@MainActivity, it.title, Toast.LENGTH_SHORT).show()
                    }
                    is ResultWrapper.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is ResultWrapper.Success -> {
                        progressBar.visibility = View.GONE

                        val data = it.data as List<DetailUserResponse>

                        if (data.isEmpty()) {
                            emptyLayout.visibility = View.VISIBLE
                        } else {
                            emptyLayout.visibility = View.GONE
                        }

                        settingRvSearch(data)

                    }
                    else -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, "Server dalam perbaikan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun settingRvSearch(users: List<DetailUserResponse>) {
        val rvGithubUserAdapter = RvAllUserAdapter()
        binding.rvGithubUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = rvGithubUserAdapter
        }

        rvGithubUserAdapter.addListUser(users)
    }

}