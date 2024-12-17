package com.example.notele.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notele.db.dao.NoteleDao
import com.example.notele.db.model.NoteleModel

@Database(entities = [NoteleModel::class], version = 2, exportSchema = false)
abstract class NoteleDataBase : RoomDatabase() {
    abstract fun getDao() : NoteleDao

}