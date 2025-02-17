package com.example.notele.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notele.data.db.model.NoteleModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotele(noteleModel: NoteleModel) // Registra nueva nota

    @Query("SELECT * FROM notele_table")
    fun getAllList(): Flow<List<NoteleModel>> //Obtenemos toda la lista de notas

    @Delete
    fun deleteNotele(notele: NoteleModel)

    @Query("SELECT * FROM notele_table WHERE idNotele = :id")
    fun getIdNote(id: Int): NoteleModel?


}