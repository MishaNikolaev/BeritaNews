package com.nmichail.beritanews.data.repository

import com.nmichail.beritanews.data.mapper.toDomain
import com.nmichail.beritanews.data.remote.NewsApi
import com.nmichail.beritanews.domain.model.Article
import com.nmichail.beritanews.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val api: NewsApi
) : NewsRepository {
    override suspend fun getTopHeadlines(country: String, apiKey: String): List<Article> {
        val response = api.getTopHeadlines(country, apiKey)
        if (response.articles.isEmpty()) {
            throw Exception("No articles found for country: $country")
        }
        return response.articles.map { it.toDomain() }
    }

    override suspend fun getTopHeadlinesByCategory(country: String, category: String, apiKey: String): List<Article> {
        val response = api.getTopHeadlinesByCategory(country, category, apiKey)
        return response.articles.map { it.toDomain() }
    }
}