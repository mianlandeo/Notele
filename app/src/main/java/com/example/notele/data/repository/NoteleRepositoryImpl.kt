package com.example.notele.data.repository

import com.example.notele.data.db.dao.NoteleDao
import com.example.notele.data.db.model.NoteleModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
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

    override fun getIdNotele(idNote: Int): NoteleModel? {
        return noteleDao.getIdNote(idNote)
    }

}