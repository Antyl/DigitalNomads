package com.antyl.digitalnomadstest.di.module

import android.content.Context
import androidx.room.Room
import com.antyl.digitalnomadstest.model.room.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(context: Context) {

    private var mContext: Context = context.applicationContext

    @Singleton
    @Provides
    fun context(): Context {
        return mContext
    }

    @Singleton
    @Provides
    fun roomDatabase(): AppDatabase {
        return Room.databaseBuilder(context(), AppDatabase::class.java,"database")
            .build()
    }
}