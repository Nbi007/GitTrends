package com.example.gittrends.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class TrendingGitResp(

    @SerializedName("total_count")
    val total: Int = 0,

    @SerializedName("items")
    val items: List<Repo> = emptyList(),

    val nextPage: Int? = null
)