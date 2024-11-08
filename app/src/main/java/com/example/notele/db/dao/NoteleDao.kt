package com.example.notele.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notele.db.model.NoteleModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotele(noteleModel: NoteleModel)

    @Query("SELECT * FROM notele_table")
    fun getAllList(): Flow<List<NoteleModel>>

    @Delete
    fun deleteNotele(notele: NoteleModel)

}