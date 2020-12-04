package com.antyl.digitalnomadstest.model.entity

import com.antyl.digitalnomadstest.model.room.ArticleEntity

data class ArticlesResponse (
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleEntity>
)