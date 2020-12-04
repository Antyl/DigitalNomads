package com.antyl.digitalnomadstest.di.module

import com.antyl.digitalnomadstest.model.repository.ArticlesRepository
import com.antyl.digitalnomadstest.presenter.ArticleListPresenter
import com.antyl.digitalnomadstest.presenter.view.ArticleListView
import com.antyl.digitalnomadstest.presenter.ImplArticleListPresenter
import dagger.Module
import dagger.Provides

@Module
class ArticleListModule(private val articleListView: ArticleListView) {

    @Provides
    fun presenter(articlesRepository: ArticlesRepository): ArticleListPresenter {
        return ImplArticleListPresenter(articleListView, articlesRepository)
    }
}