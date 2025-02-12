package com.example.notele.domain.model

sealed class NoteleType {
    data object Ascending: NoteleType()
    data object Descending: NoteleType()
}