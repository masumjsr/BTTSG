package com.mssoftinc.bttsg.database.di

import com.mssoftinc.bttsg.database.AppDatabase
import com.mssoftinc.bttsg.database.dao.CategoryDao
import com.mssoftinc.bttsg.database.dao.QuestionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun provideCategoryDao(
        database: AppDatabase
    ): CategoryDao = database.getCategoryDao()

    @Provides
    fun provideWallpaperDao(
        database: AppDatabase
    ): QuestionDao = database.getWallpaperDao()
}