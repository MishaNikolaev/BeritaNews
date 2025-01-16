package com.nmichail.beritanews.data.mapper

import com.nmichail.beritanews.data.dto.ArticleDto
import com.nmichail.beritanews.domain.model.Article

fun ArticleDto.toDomain(): Article {
    return Article(
        source = source.name,
        author = author ?: "Unknown",
        title = title,
        description = description ?: "No description available",
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content ?: "No content available"
    )
}