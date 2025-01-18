package com.nmichail.beritanews.domain.repository

import com.nmichail.beritanews.domain.model.Article

interface NewsRepository {

    suspend fun getTopHeadlines(country: String, apiKey: String): List<Article>

    suspend fun getTopHeadlinesByCategory(country: String, category: String, apiKey: String): List<Article>
}