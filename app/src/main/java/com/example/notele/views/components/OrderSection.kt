package com.example.notele.views.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notele.usecases.model.NoteleOrder
import com.example.notele.usecases.model.NoteleType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    order: NoteleOrder = NoteleOrder.Date(noteleType = NoteleType.Descending),
    onChangeSelect: (NoteleOrder) -> Unit
){
    Column(
        modifier = modifier
    ) {
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            RadioButtonComponent(
                select = order is NoteleOrder.Title,
                onSelect = {onChangeSelect(NoteleOrder.Title(noteleType = order.noteleType)) },
                text = "Titulo"
            )
            Spacer(modifier = Modifier.width(8.dp))
            RadioButtonComponent(
                select = order is NoteleOrder.Date,
                onSelect = {onChangeSelect(NoteleOrder.Date(noteleType = order.noteleType)) },
                text = "Fecha"
            )
            Spacer(modifier = Modifier.width(8.dp))
            RadioButtonComponent(
                select = order is NoteleOrder.ColorPriority,
                onSelect = {onChangeSelect(NoteleOrder.ColorPriority(noteleType = order.noteleType)) },
                text = "Color"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            RadioButtonComponent(
                select = order.noteleType is NoteleType.Ascending,
                onSelect = { onChangeSelect(order.copy(
                    NoteleType.Ascending
                )) },
                text = "Ascendente"
            )
            Spacer(modifier = Modifier.width(8.dp))
            RadioButtonComponent(
                select = order.noteleType is NoteleType.Descending,
                onSelect = {onChangeSelect(order.copy(
                    NoteleType.Descending
                ))},
                text = "Descendente"
            )
        }
    }
}