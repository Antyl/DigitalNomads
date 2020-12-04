package com.antyl.digitalnomadstest

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.antyl.digitalnomadstest.di.component.AppComponent
import com.antyl.digitalnomadstest.di.component.DaggerAppComponent
import com.antyl.digitalnomadstest.di.module.AppModule
import com.antyl.digitalnomadstest.di.module.RepositoryModule
import com.antyl.digitalnomadstest.model.room.AppDatabase

class App: Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        setup()
    }

    private fun setup() {
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .repositoryModule(RepositoryModule())
            .build()
        component.inject(this)

        appDatabase = component.getAppDatabase()
    }

    fun getAppComponent(): AppComponent {
        return component
    }

    fun getAppDatabase(): AppDatabase {
        return appDatabase
    }

    companion object {
        lateinit var instance: App private set
        lateinit var appDatabase: AppDatabase
    }
}