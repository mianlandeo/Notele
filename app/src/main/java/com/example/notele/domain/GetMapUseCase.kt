package com.example.notele.domain

import com.example.notele.data.db.model.NoteleModel
import com.example.notele.data.repository.NoteleRepository
import com.example.notele.domain.model.NoteleOrder
import com.example.notele.domain.model.NoteleType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMapUseCase(
    private val repository: NoteleRepository
) {
    operator fun invoke(
        /*take a order of default descending*/
        order: NoteleOrder = NoteleOrder.Date(NoteleType.Descending)
    ) : Flow<List<NoteleModel>>{
        /*map he value and return a copy of the list
        * according to order -> Descending & Ascending */
        return repository.getAllNotes().map { valueList ->
            when(order.noteleType) {
                /*order according to term seleccion for the user*/
                 is NoteleType.Ascending -> {
                     when(order) {
                         is NoteleOrder.Title -> valueList.sortedBy { it.title.lowercase() }
                         is NoteleOrder.Date -> valueList.sortedBy { it.time }
                         is NoteleOrder.ColorPriority -> valueList.sortedBy { it.color }
                     }
                 }
                is NoteleType.Descending -> {
                    when(order) {
                        is NoteleOrder.Title -> valueList.sortedByDescending { it.title }
                        is NoteleOrder.Date -> valueList.sortedByDescending { it.time }
                        is NoteleOrder.ColorPriority -> valueList.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}