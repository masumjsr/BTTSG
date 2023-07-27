package com.mssoftinc.bttsg.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mssoftinc.bttsg.database.dao.CategoryDao
import com.mssoftinc.bttsg.database.dao.QuestionDao
import com.mssoftinc.bttsg.model.Question
import com.mssoftinc.bttsg.model.Category

@Database(
    entities = [
        Category::class,
        Question::class,
    ],
    version = 1,
    exportSchema =  true
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getWallpaperDao(): QuestionDao
    abstract fun getCategoryDao(): CategoryDao

}