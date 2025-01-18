package com.nmichail.beritanews.data.mapper

import com.nmichail.beritanews.data.dto.ArticleDto
import com.nmichail.beritanews.data.local.ArticleEntity
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
        content = content ?: "No content available",
        category = "business"
    )
}

fun ArticleEntity.toDomain(): Article {
    return Article(
        source = source,
        author = author,
        title = title,
        description = description,
        url = "",
        urlToImage = null,
        publishedAt = publishedAt,
        content = null,
        category = ""
    )
}

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        id = title.hashCode().toString(),
        title = title,
        description = description,
        author = author,
        publishedAt = publishedAt,
        source = source
    )
}