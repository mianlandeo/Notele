package com.example.notele.presentation.views.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditTextVisible(
    modifier: Modifier = Modifier,
    text:String,
    hint: String,
    tag:String = "",
    focusState: (FocusState) -> Unit,
    onValueText: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = true,
    hintVisible: Boolean = true,
    keyBoardOption: KeyboardOptions,
    maxLine: Int
){

    Box(
        modifier = modifier
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueText,
            singleLine = singleLine,
            textStyle = textStyle,
            keyboardOptions = keyBoardOption,
            maxLines = maxLine,
            modifier = Modifier
                .testTag(tag)
                .padding(8.dp)
                .onFocusChanged {
                    focusState(it)
                }
                .height(IntrinsicSize.Max)
        )
        if (hintVisible) {
            Text(
                text = hint,
                style = textStyle,
                color = Color.DarkGray,
                modifier = Modifier.padding(8.dp),
                fontSize = 18.sp
            )
        }
    }
}