package com.example.notele.presentation.viewmodel

import com.example.notele.data.db.model.NoteleModel
import com.example.notele.domain.model.NoteleOrder

sealed class NoteleEvent {
    data class Order(val noteOrder: NoteleOrder): NoteleEvent()
    data class Delete(val notele: NoteleModel): NoteleEvent()
    data object RestoreNote: NoteleEvent() /*Resguarda la ultima nota eliminada por un corto tiempo*/
    data object ToggleOrderSection: NoteleEvent()
}