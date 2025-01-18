package com.nmichail.beritanews.data.repository

import com.nmichail.beritanews.data.local.ArticleDao
import com.nmichail.beritanews.data.mapper.toDomain
import com.nmichail.beritanews.data.mapper.toEntity
import com.nmichail.beritanews.domain.model.Article
import com.nmichail.beritanews.domain.repository.LocalRepository
import javax.inject.Inject

class LocalNewsRepositoryImpl @Inject constructor(
    private val articleDao: ArticleDao
) : LocalRepository {

    override suspend fun getAllArticles(): List<Article> {
        return articleDao.getAllArticles().map { it.toDomain() }
    }

    override suspend fun saveArticles(articles: List<Article>) {
        articleDao.insertArticles(articles.map { it.toEntity() })
    }

    override suspend fun clearArticles() {
        articleDao.clearArticles()
    }

    override suspend fun getArticleById(id: String): Article? {
        return articleDao.getArticleById(id)?.toDomain()
    }
}