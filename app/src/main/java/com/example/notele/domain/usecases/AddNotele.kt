package com.example.notele.domain.usecases

import com.example.notele.data.db.model.MessageException
import com.example.notele.data.db.model.NoteleModel
import com.example.notele.data.repository.NoteleRepository

class AddNotele(
    private val repository: NoteleRepository
) {

    @Throws(MessageException::class)
    operator fun invoke(noteleModel: NoteleModel) {
        if (noteleModel.title.isBlank()){
            throw MessageException("Falta ingresar datos")
        } else {
            repository.insertNote(noteleModel)
        }
    }
}