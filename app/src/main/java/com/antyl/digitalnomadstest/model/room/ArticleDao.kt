package com.antyl.digitalnomadstest.model.room

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ArticleDao {

    @Query("SELECT * FROM ArticleEntity")
    fun getArticles(): Single<List<ArticleEntity>>

    @Query("SELECT * FROM ArticleEntity")
    fun getArticlesRoom(): DataSource.Factory<Int, ArticleEntity>

    @Insert
    fun insertArticles(articles: List<ArticleEntity>): Completable
}