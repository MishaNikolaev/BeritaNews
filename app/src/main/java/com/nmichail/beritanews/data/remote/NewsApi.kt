package com.nmichail.beritanews.data.remote

import com.nmichail.beritanews.data.dto.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): NewsDto
}