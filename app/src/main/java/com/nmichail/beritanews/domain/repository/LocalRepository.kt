package com.nmichail.beritanews.domain.repository

import com.nmichail.beritanews.domain.model.Article

interface LocalRepository {

    suspend fun getAllArticles(): List<Article>

    suspend fun saveArticles(articles: List<Article>)

    suspend fun clearArticles()

    suspend fun getArticleById(id: String): Article?
}