package com.mssoftinc.bttsg.data.repository

import com.mssoftinc.bttsg.database.dao.CategoryDao
import com.mssoftinc.bttsg.model.Category
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryDao: CategoryDao){
    suspend fun upsertCategory(category: Category):Long=categoryDao.upsertCategory(category)
    fun getCategory(id:Long)=categoryDao.getCategory(id)
    fun getCategory(type:String)=categoryDao.getAllCategory(type)
    fun updateLastAttemptScore(categoryId: Long, score: Int)=categoryDao.updateLastAttemptScore(categoryId, score)
    fun getHistoryCategory()=categoryDao.getHistoryCategory()
}