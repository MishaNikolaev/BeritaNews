package com.nmichail.beritanews.domain.usecases

import com.nmichail.beritanews.domain.model.Article
import com.nmichail.beritanews.domain.repository.LocalRepository
import javax.inject.Inject

class SaveArticlesToLocalDatabaseUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(articles: List<Article>) {
        localRepository.saveArticles(articles)
    }
}