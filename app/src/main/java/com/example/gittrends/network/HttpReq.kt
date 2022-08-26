package com.example.gittrends.network

import com.google.gson.annotations.SerializedName

class HttpReq<T> {
    @SerializedName("body")
    var body: T? = null
        private set

    fun setBody(body: T) {
        this.body = body
    }

    constructor(body: T) {
        this.body = body
    }

    constructor() {}
}