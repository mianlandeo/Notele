package com.example.notele.usecases.model

import com.example.notele.db.model.NoteleModel

sealed class NoteleEvent {
    data class Order(val noteOrder: NoteleOrder): NoteleEvent()
    data class Delete(val notele: NoteleModel): NoteleEvent()
    data object RestoreNote: NoteleEvent() /*Resguarda la nota eliminada por unos segundos*/
    data object ToggleOrderSection: NoteleEvent()
}