package com.ikrima.task.githubuserapp.ui.contents.detailuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ikrima.task.githubuserapp.data.responses.RepositoryResponses
import com.ikrima.task.githubuserapp.databinding.ItemRepositoryUserBinding
import com.ikrima.task.githubuserapp.utils.helper.AndroidUtils
import java.util.*


class RvUserRepositoryAdapter : RecyclerView.Adapter<RvUserRepositoryAdapter.RvUserRepositoryViewHolder>() {

    private val listRepos = ArrayList<RepositoryResponses>()

    fun addListRepo(list : List<RepositoryResponses>) {
        this.listRepos.clear()
        this.listRepos.addAll(list)
    }

    inner class RvUserRepositoryViewHolder(private val binding : ItemRepositoryUserBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(position : Int) {
            binding.apply {
                val dataNow = listRepos[position]

                tvTitleRepository.text = dataNow.name

                if (dataNow.description.isNullOrEmpty()) {
                    tvDescRepository.text = String.format("No Description")
                } else {
                    tvDescRepository.text = dataNow.description
                }

                tvNumberOfStars.text = dataNow.numberOfStar.toString()

                val updatedAt = AndroidUtils.getUpdatedTimeFormatter(dataNow.updatedAt?:"")
                if (updatedAt.contains("days", false) || updatedAt.contains("hours", false)) {
                    tvUpdatedRepository.text = String.format("Updated $updatedAt")
                } else {
                    tvUpdatedRepository.text = String.format("Updated on $updatedAt")
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvUserRepositoryViewHolder {
        return RvUserRepositoryViewHolder(
            ItemRepositoryUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RvUserRepositoryViewHolder, position: Int) {
        return holder.bind(position)
    }

    override fun getItemCount(): Int {
        return listRepos.size
    }
}