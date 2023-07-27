package com.mssoftinc.bttsg.data.repository

import com.mssoftinc.bttsg.database.dao.QuestionDao
import com.mssoftinc.bttsg.model.Question
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val questionDao: QuestionDao){
    suspend fun upsertQuestion(question: List<Question>)=questionDao.upsertQuestion(question)
    fun getQuestion(categoryId:Long)=questionDao.getQuestion(categoryId)
    fun updateAttempt(id: Int, answer: Int)=questionDao.updateAttempt(id, answer)
}