package com.example.notele.domain.model

sealed class NoteleOrder(val noteleType: NoteleType) {

    /*Restrict the possible sub class*/
    class Title(noteleType: NoteleType): NoteleOrder(noteleType)
    class Date(noteleType: NoteleType): NoteleOrder(noteleType)
    class ColorPriority(noteleType: NoteleType): NoteleOrder(noteleType)
    fun copy(noteleType: NoteleType): NoteleOrder{
        /*Return sub class NoteleOrder and criterion represent in order */
        return when(this) {
            is Title -> Title(noteleType)
            is Date -> Date(noteleType)
            is ColorPriority -> ColorPriority(noteleType)
        }
    }
}

