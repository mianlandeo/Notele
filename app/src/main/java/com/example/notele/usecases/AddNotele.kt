package com.example.notele.usecases

import com.example.notele.db.model.MessageException
import com.example.notele.db.model.NoteleModel
import com.example.notele.repository.NoteleRepository

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