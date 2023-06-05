package com.example.gittrends.network

import com.example.gittrends.model.Repo
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_USER_PATH_URL = "/git/users"
const val DOUBLE_QUOTES = "\""

interface TestApis {

    @GET(BASE_USER_PATH_URL)
    suspend fun getAllUsers(): List<User>

    @GET("$BASE_USER_PATH_URL/{id}")
    suspend fun getUserById(@Path("id") id: Int): User
}

data class User(
    val id: Int,
    val name: String,
    val city: String,
    val country: String
)