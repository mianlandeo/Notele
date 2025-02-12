package com.example.notele.domain.usecases

import com.example.notele.data.db.model.NoteleModel
import com.example.notele.data.repository.NoteleRepository

class GetIdNote (
    private val repository: NoteleRepository
) {

    operator fun invoke(idNotele: Int): NoteleModel?{
        return repository.getIdNotele(idNotele)
    }
}