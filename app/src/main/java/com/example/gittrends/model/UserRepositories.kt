package com.example.gittrends.model

import com.example.gittrends.network.User

interface UserRepositories {
    suspend fun getAllUsers(): ApiResponse<List<User>>

    suspend fun getUserById(id: Int): ApiResponse<User>
}