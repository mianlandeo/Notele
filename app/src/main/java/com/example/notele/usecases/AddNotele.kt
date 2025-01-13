package com.example.notele.usecases

import android.util.Log
import com.example.notele.db.model.MessageException
import com.example.notele.db.model.NoteleModel
import com.example.notele.repository.NoteleRepository

class AddNotele(
    private val repository: NoteleRepository
) {

    @Throws(MessageException::class)
    operator fun invoke(noteleModel: NoteleModel) {
        if (noteleModel.title.isBlank() && noteleModel.description.isBlank()){
            throw MessageException("Falta ingresar datos")
        } else {
            repository.insertNote(noteleModel)
            Log.e("AddNotele: ", "${repository.insertNote(noteleModel)}")
        }
    }
}