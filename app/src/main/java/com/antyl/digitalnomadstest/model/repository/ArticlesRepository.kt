package com.antyl.digitalnomadstest.model.repository

import androidx.paging.DataSource
import com.antyl.digitalnomadstest.model.entity.Status
import com.antyl.digitalnomadstest.model.room.ArticleEntity
import io.reactivex.Single

interface ArticlesRepository {
    companion object{
        const val PAGE_LIMITS = 20
    }

    fun fetchArticles(): Single<Status>
    fun getArticlesFromDatabase(): Single<DataSource.Factory<Int, ArticleEntity>>
}