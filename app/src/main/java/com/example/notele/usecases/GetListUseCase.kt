package com.example.notele.usecases

import com.example.notele.db.model.NoteleModel
import com.example.notele.repository.NoteleRepository
import com.example.notele.usecases.model.NoteleOrder
import com.example.notele.usecases.model.NoteleType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListUseCase(
    private val repository: NoteleRepository
) {
    operator fun invoke(
        order: NoteleOrder = NoteleOrder.Date(NoteleType.Descending)
    ) : Flow<List<NoteleModel>>{
        /*Devuelve un mapeo de toda la lista de typo ascendente o descendente segun su orden*/
        return repository.getAllNotes().map { valueList ->
            when(order.noteleType) {
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