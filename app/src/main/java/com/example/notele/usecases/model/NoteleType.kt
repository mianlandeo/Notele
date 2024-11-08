package com.example.notele.usecases.model


sealed class NoteleType {
    data object Ascending: NoteleType()
    data object Descending: NoteleType()
}