package com.antyl.digitalnomadstest.di.component

import com.antyl.digitalnomadstest.App
import com.antyl.digitalnomadstest.di.module.AppModule
import com.antyl.digitalnomadstest.di.module.RepositoryModule
import com.antyl.digitalnomadstest.model.repository.ArticlesRepository
import com.antyl.digitalnomadstest.model.room.AppDatabase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(app: App)
    fun getArticlesRepository(): ArticlesRepository
    fun getAppDatabase(): AppDatabase
}