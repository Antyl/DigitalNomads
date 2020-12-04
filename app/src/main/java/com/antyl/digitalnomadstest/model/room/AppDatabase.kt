package com.antyl.digitalnomadstest.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArticleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticleDao
}