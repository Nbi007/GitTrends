package com.example.gittrends.interfaces

import com.example.gittrends.model.TrendingGitResp
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    @GET("search/repositories?sort=stars")
    suspend fun getTrendingRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): TrendingGitResp

}