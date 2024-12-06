package com.example.notele.usecases

import com.example.notele.db.model.NoteleModel
import com.example.notele.repository.NoteleRepository

class GetIdNote (
    private val repository: NoteleRepository
) {

    suspend operator fun invoke(idNotele: Int): NoteleModel?{
        return repository.getIdNotele(idNotele)
    }
}