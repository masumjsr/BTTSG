package com.mssoftinc.bttsg.database.di

import com.mssoftinc.bttsg.data.repository.CategoryRepository
import com.mssoftinc.bttsg.data.repository.QuestionRepository
import com.mssoftinc.bttsg.database.dao.CategoryDao
import com.mssoftinc.bttsg.database.dao.QuestionDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
}