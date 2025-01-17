package com.example.notele.usecases.model

sealed class NoteleOrder(val noteleType: NoteleType) {

    class Title(noteleType: NoteleType): NoteleOrder(noteleType)
    class Date(noteleType: NoteleType): NoteleOrder(noteleType)
    class ColorPriority(noteleType: NoteleType): NoteleOrder(noteleType)

    fun copy(noteleType: NoteleType): NoteleOrder{
        return when(this) {
            is Title -> Title(noteleType)
            is Date -> Date(noteleType)
            is ColorPriority -> ColorPriority(noteleType)
        }
    }
}

