package com.example.notele.data.repository

import com.example.notele.data.db.model.NoteleModel
import kotlinx.coroutines.flow.Flow

interface NoteleRepository {

    fun getAllNotes(): Flow<List<NoteleModel>>

    fun insertNote(noteleModel: NoteleModel)

    fun deleteNote(noteleModel: NoteleModel)

    fun getIdNotele(idNote : Int): NoteleModel?

}