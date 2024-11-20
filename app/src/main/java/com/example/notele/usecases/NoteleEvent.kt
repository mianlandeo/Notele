package com.example.notele.usecases

import com.example.notele.db.model.NoteleModel
import com.example.notele.usecases.model.NoteleOrder

sealed class NoteleEvent {
    data class Order(val noteOrder: NoteleOrder): NoteleEvent()
    data class Delete(val notele: NoteleModel): NoteleEvent()
    data object RestoreNote: NoteleEvent()
    data object ToggleOrderSection: NoteleEvent()
}