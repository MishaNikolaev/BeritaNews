package com.nmichail.beritanews.data.dto

data class NewsDto(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto>
)
