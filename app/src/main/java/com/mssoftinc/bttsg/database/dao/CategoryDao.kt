package com.mssoftinc.bttsg.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.mssoftinc.bttsg.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Transaction
    @Query(
        value ="Select * from category"
    )
    fun getAllCategories() : Flow<List<Category>>

    @Transaction
    @Query(
        value ="Select * from category where type=:type"
    )
    fun getAllCategory(type:String) : Flow<List<Category>>


    @Transaction
    @Query(
        value ="Select * from category where lastModified!=-1 order by lastModified  desc"
    )
    fun getHistoryCategory() : Flow<List<Category>>
    @Transaction
    @Query(
        value ="Select * from category where id=:id"
    )
    fun getCategory(id:Long) : Flow<Category>
    @Upsert
    suspend fun upsertCategory(categories: List<Category>)
    @Upsert
    suspend fun upsertCategory(categories: Category):Long

    @Transaction
    @Query(
        value ="Update category set last_attempt=:score , lastModified=:lastModified where id=:id"
    )
    fun updateLastAttemptScore(id:Long,score:Int,lastModified:Long=System.currentTimeMillis())
}