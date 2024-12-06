package com.example.notele.views.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun EditTextVisible(
    text:String,
    hint: String,
    tag:String = "",
    focusState: (FocusState) -> Unit,
    onValueText: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean,
    hintVisible: Boolean = true,
    modifier: Modifier = Modifier
){

    Box(
        modifier = modifier
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueText,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier
                .testTag(tag)
                .padding(8.dp)
                .onFocusChanged {
                    focusState(it)
                }
        )
        if (hintVisible) {
            Text(
                text = hint,
                style = textStyle,
                color = Color.DarkGray
            )
        }
    }

}