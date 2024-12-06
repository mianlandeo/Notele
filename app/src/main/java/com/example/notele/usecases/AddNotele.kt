package com.example.notele.usecases

import com.example.notele.db.model.MessageException
import com.example.notele.db.model.NoteleModel
import com.example.notele.repository.NoteleRepository

class AddNotele(
    private val repository: NoteleRepository
) {

    operator fun invoke(noteleModel: NoteleModel) {
        if (noteleModel.title.isBlank()){
            throw MessageException("Ingrese el titulo")
        } else if (noteleModel.description.isBlank()){
            throw MessageException("Ingrese la descripción")
        }
        repository.insertNote(noteleModel)
    }
}