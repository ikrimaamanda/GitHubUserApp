package com.ikrima.task.githubuserapp.ui.contents.maincontents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ikrima.task.githubuserapp.data.responses.DetailUserResponse
import com.ikrima.task.githubuserapp.databinding.ItemGithubUserBinding


class RvAllUserAdapter : RecyclerView.Adapter<RvAllUserAdapter.RvAllUserViewHolder>() {

    private val listSearchUser = ArrayList<DetailUserResponse>()

    fun addListUser(list : List<DetailUserResponse>) {
        this.listSearchUser.clear()
        this.listSearchUser.addAll(list)
    }

    inner class RvAllUserViewHolder(private val binding : ItemGithubUserBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(position : Int) {
            binding.apply {
                val dataNow = listSearchUser[position]
                tvItemNameGithubUser.text = dataNow.username


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAllUserViewHolder {
        return RvAllUserViewHolder(
            ItemGithubUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RvAllUserViewHolder, position: Int) {
        return holder.bind(position)
    }

    override fun getItemCount(): Int {
        return listSearchUser.size
    }
}