package com.ikrima.task.githubuserapp.data.responses

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
    @SerializedName("login") val username : String? = "",
    @SerializedName("avatar_url") val avatarURL : String? = "",
    val name : String? = "",
    val bio : String? = "",
    val description : String? = ""
)
