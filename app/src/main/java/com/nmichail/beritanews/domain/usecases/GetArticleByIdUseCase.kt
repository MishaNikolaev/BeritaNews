package com.nmichail.beritanews.domain.usecases

import com.nmichail.beritanews.domain.model.Article
import com.nmichail.beritanews.domain.repository.LocalRepository
import javax.inject.Inject

class GetArticleByIdUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(id: String): Article? {
        return localRepository.getArticleById(id)
    }
}