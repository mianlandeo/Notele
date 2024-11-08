package com.example.notele.usecases.model

sealed class NoteleOrder(val noteleType: NoteleType) {

    class Title(noteleType: NoteleType): NoteleOrder(noteleType)
    class Description(noteleType: NoteleType): NoteleOrder(noteleType)
    class Date(noteleType: NoteleType): NoteleOrder(noteleType)
    class Priority(noteleType: NoteleType): NoteleOrder(noteleType)

}