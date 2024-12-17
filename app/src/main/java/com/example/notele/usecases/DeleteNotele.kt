package com.example.notele.usecases

import com.example.notele.db.model.NoteleModel
import com.example.notele.repository.NoteleRepository
import com.example.notele.repository.NoteleRepositoryImpl
import javax.inject.Inject

class DeleteNotele @Inject constructor(
    private val repository: NoteleRepository
) {

    operator fun invoke(notele: NoteleModel) {
        repository.deleteNote(notele)
    }
}