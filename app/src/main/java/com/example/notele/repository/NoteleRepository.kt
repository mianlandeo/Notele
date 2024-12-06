package com.example.notele.repository

import com.example.notele.db.model.NoteleModel
import kotlinx.coroutines.flow.Flow

interface NoteleRepository {

    fun getAllNotes(): Flow<List<NoteleModel>>

    fun insertNote(noteleModel: NoteleModel)

    fun deleteNote(noteleModel: NoteleModel)

    fun getIdNotele(idNote : Int): NoteleModel?

}