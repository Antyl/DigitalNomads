package com.antyl.digitalnomadstest.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    var publishedAt: String
)