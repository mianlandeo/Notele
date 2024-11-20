package com.example.notele.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notele.usecases.model.NoteleOrder
import com.example.notele.usecases.model.NoteleType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    selectOrder: NoteleOrder = NoteleOrder.Date(noteleType = NoteleType.Descending),
    onChangeSelect: (NoteleOrder) -> Unit,
){
    Column(
        modifier = modifier
    ) {
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {

            RadioButtonComponent(
                select = selectOrder is NoteleOrder.Title,
                onSelect = {onChangeSelect(NoteleOrder.Title(noteleType = selectOrder.noteleType)) },
                text = "Titulo"
            )
            RadioButtonComponent(
                select = selectOrder is NoteleOrder.Date,
                onSelect = {onChangeSelect(NoteleOrder.Date(noteleType = selectOrder.noteleType)) },
                text = "Fecha"
            )
            RadioButtonComponent(
                select = selectOrder is NoteleOrder.ColorPriority,
                onSelect = {onChangeSelect(NoteleOrder.ColorPriority(noteleType = selectOrder.noteleType)) },
                text = "Color"
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row {
                RadioButtonComponent(
                    select = selectOrder.noteleType is NoteleType.Ascending,
                    onSelect = {onChangeSelect(selectOrder.copy(
                        NoteleType.Ascending
                    )) },
                    text = "Ascendente"
                )
                Spacer(modifier = Modifier.height(6.dp))
                RadioButtonComponent(
                    select = selectOrder.noteleType is NoteleType.Descending,
                    onSelect = {onChangeSelect(selectOrder.copy(
                        NoteleType.Descending
                    )) },
                    text = "Descendente"
                )
            }
        }
    }
}