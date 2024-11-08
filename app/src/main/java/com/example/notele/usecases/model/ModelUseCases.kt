package com.example.notele.usecases.model

import com.example.notele.usecases.DeleteNotele
import com.example.notele.usecases.GetListUseCase

data class ModelUseCases (
    val getList : GetListUseCase,
    val deleteNotele: DeleteNotele
)