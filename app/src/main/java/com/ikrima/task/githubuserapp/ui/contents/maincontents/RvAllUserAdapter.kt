package com.ikrima.task.githubuserapp.ui.contents.maincontents

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ikrima.task.githubuserapp.data.responses.DetailUserResponse
import com.ikrima.task.githubuserapp.databinding.ItemGithubUserBinding
import com.ikrima.task.githubuserapp.ui.contents.detailuser.DetailUserActivity
import com.ikrima.task.githubuserapp.utils.helper.UIUtils.loadImage


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

                civItemGithubUser.loadImage(dataNow.avatarURL, itemView.context, progressBar)

                itemGithubUserLayout.setOnClickListener {
                    val i = Intent(itemView.context, DetailUserActivity::class.java)
                    i.apply {
                        putExtra("username", dataNow.username)
                    }
                    itemView.context.startActivity(i)
                }
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