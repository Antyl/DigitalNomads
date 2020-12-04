package com.antyl.digitalnomadstest.di.module

import com.antyl.digitalnomadstest.model.repository.ArticlesRepository
import com.antyl.digitalnomadstest.model.repository.ImplArticlesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun getArticlesRepository(): ArticlesRepository {
        return ImplArticlesRepository()
    }
}