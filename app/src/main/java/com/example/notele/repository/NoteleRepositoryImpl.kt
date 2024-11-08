package com.example.notele.repository

import com.example.notele.db.dao.NoteleDao
import com.example.notele.db.model.NoteleModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteleRepositoryImpl @Inject constructor(
    private val noteleDao: NoteleDao
): NoteleRepository {

    override fun getAllNotes(): Flow<List<NoteleModel>> {
        return noteleDao.getAllList()
    }

    override fun insertNote(noteleModel: NoteleModel) {
        noteleDao.insertNotele(noteleModel)
    }

    override fun deleteNote(noteleModel: NoteleModel) {
        noteleDao.deleteNotele(noteleModel)
    }

}