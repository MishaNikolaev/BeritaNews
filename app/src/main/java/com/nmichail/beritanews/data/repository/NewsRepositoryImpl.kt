package com.nmichail.beritanews.data.repository

import com.nmichail.beritanews.data.mapper.toDomain
import com.nmichail.beritanews.data.remote.NewsApi
import com.nmichail.beritanews.domain.model.Article
import com.nmichail.beritanews.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val api: NewsApi
) : NewsRepository {
    override suspend fun getTopHeadlines(country: String, apiKey: String): List<Article> {
        return api.getTopHeadlines(country, apiKey).articles.map { it.toDomain() }
    }
}