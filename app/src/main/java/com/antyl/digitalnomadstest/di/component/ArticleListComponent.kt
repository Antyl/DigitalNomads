package com.antyl.digitalnomadstest.di.component

import com.antyl.digitalnomadstest.di.AppScope
import com.antyl.digitalnomadstest.di.module.ArticleListModule
import com.antyl.digitalnomadstest.ui.ArticleListFragment
import dagger.Component

@AppScope
@Component(modules = [ArticleListModule::class], dependencies = [AppComponent::class])
interface ArticleListComponent {
    fun inject(articleListFragment: ArticleListFragment)
}