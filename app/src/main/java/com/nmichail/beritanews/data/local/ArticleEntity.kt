package com.nmichail.beritanews.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val author: String?,
    val publishedAt: String,
    val source: String
)