package com.example.notele.usecases.model

import com.example.notele.usecases.AddNotele
import com.example.notele.usecases.DeleteNotele
import com.example.notele.usecases.GetIdNote
import com.example.notele.usecases.GetListUseCase

data class ModelUsesCases (
    val getAllList : GetListUseCase,
    val deleteNotele: DeleteNotele,
    val addNotele: AddNotele,
    val getIdNote: GetIdNote
)