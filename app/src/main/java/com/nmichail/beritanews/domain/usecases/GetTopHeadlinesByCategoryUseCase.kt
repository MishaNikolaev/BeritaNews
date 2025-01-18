package com.nmichail.beritanews.domain.usecases

import com.nmichail.beritanews.domain.model.Article
import com.nmichail.beritanews.domain.repository.NewsRepository
import javax.inject.Inject

class GetTopHeadlinesByCategoryUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(country: String, category: String, apiKey: String): List<Article> {
        return repository.getTopHeadlinesByCategory(country, category, apiKey)
    }
}