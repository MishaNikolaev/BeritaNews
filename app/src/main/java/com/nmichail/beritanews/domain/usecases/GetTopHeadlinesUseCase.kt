package com.nmichail.beritanews.domain.usecases

import com.nmichail.beritanews.domain.model.Article
import com.nmichail.beritanews.domain.repository.NewsRepository

class GetTopHeadlinesUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(country: String, apiKey: String): List<Article> {
        return repository.getTopHeadlines(country, apiKey)
    }
}