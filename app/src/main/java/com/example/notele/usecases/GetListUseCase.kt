package com.example.notele.usecases

import com.example.notele.db.model.NoteleModel
import com.example.notele.repository.NoteleRepository
import com.example.notele.usecases.model.NoteleOrder
import com.example.notele.usecases.model.NoteleType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetListUseCase @Inject constructor(
    private val repository: NoteleRepository
) {
    operator fun invoke(
        order: NoteleOrder = NoteleOrder.Date(NoteleType.Descending)
    ) : Flow<List<NoteleModel>>{
        return repository.getAllNotes().map { valueList ->
            when(order.noteleType) {
                /*Devuelve una lista de manera acendente*/
                 is NoteleType.Ascending -> {
                     when(order) {
                         is NoteleOrder.Title -> valueList.sortedBy { it.title }
                         is NoteleOrder.Description -> valueList.sortedBy { it.description }
                         is NoteleOrder.Date -> valueList.sortedBy { it.date }
                         is NoteleOrder.Priority -> valueList.sortedBy { it.priority }
                     }
                 }
                is NoteleType.Descending -> {
                    when(order) {
                        is NoteleOrder.Title -> valueList.sortedByDescending { it.title }
                        is NoteleOrder.Description -> valueList.sortedByDescending { it.description }
                        is NoteleOrder.Date -> valueList.sortedByDescending { it.date }
                        is NoteleOrder.Priority -> valueList.sortedByDescending { it.priority }
                    }
                }
            }
        }
    }
}