package com.ikrima.task.githubuserapp.data.responses

import com.google.gson.annotations.SerializedName

data class RepositoryResponses(
    val name : String? = "",
    @SerializedName("html_url") val htmlUrl : String? = "",
    val description : String? = "",
    @SerializedName("updated_at") val updatedAt : String? = "",
    @SerializedName("stargazers_count") val numberOfStar : Int = 0
)