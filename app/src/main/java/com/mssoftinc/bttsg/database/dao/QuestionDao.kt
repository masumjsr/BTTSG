package com.mssoftinc.bttsg.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.mssoftinc.bttsg.model.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Transaction
    @Query(
        value ="Select * from question where category=:categoryId"
    )
    fun getQuestion(categoryId:Long) : Flow<List<Question>>

    @Transaction
    @Query(
        value ="Update question set lastAnswer=:answer where id=:id"
    )
    fun updateAttempt(id:Int,answer:Int)
    @Upsert
    suspend fun upsertQuestion(wallpapers: List<Question>)
    @Upsert
    suspend fun upsertQuestion(wallpapers: Question)
}