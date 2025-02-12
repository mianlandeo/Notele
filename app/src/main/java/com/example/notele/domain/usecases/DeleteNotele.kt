package com.example.notele.domain.usecases

import com.example.notele.data.db.model.NoteleModel
import com.example.notele.data.repository.NoteleRepository
import javax.inject.Inject

class DeleteNotele @Inject constructor(
    private val repository: NoteleRepository
) {

    operator fun invoke(notele: NoteleModel) {
        repository.deleteNote(notele)
    }
}