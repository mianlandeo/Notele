package com.example.notele.presentation.viewmodel

import androidx.compose.ui.focus.FocusState

sealed class AddEditNotelesEvent {

    data class Title(val value: String): AddEditNotelesEvent()
    data class TitleChange(val valueChange: FocusState): AddEditNotelesEvent()
    data class Description(val value: String): AddEditNotelesEvent()
    data class DescriptionChange(val valueChange: FocusState): AddEditNotelesEvent()
    data class Color(val value : Int): AddEditNotelesEvent()
    data object OnSaveItem: AddEditNotelesEvent()

}