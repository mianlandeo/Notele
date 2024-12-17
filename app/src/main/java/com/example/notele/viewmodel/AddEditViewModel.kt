package com.example.notele.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notele.db.model.NoteleModel
import com.example.notele.usecases.model.ModelUsesCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val usesCases: ModelUsesCases,
    savedStateHandle : SavedStateHandle
): ViewModel() {

    private val _editTitle = mutableStateOf(StateEditTextField(
        hint = "Ingrese Titulo"
    ))
    val editTitle : State<StateEditTextField> = _editTitle

    private val _editDescription = mutableStateOf(StateEditTextField(
        hint = "Ingrese Descripción"
    ))
    val editDescription : State<StateEditTextField> = _editDescription

    // -> mutableStateOf ó mutableIntStateOf
    private val _priorityColor = mutableIntStateOf(NoteleModel.notelePriority.random().toArgb())
    val priorityColor : State<Int> = _priorityColor

    private val _stateEvent = MutableSharedFlow<UiEvent>()
    val stateEvent = _stateEvent.asSharedFlow()

    private var currendId : Int? = null

    init {

        savedStateHandle.get<Int>("noteleId")?.let { id ->
            if (id != -1){
                viewModelScope.launch(Dispatchers.IO) {
                    usesCases.getIdNote(id)?.also { notele ->
                        currendId = notele.idNotele
                        _editTitle.value = editTitle.value.copy(
                            text = notele.title,
                            isHintVisible = false
                        )
                        _editDescription.value = editDescription.value.copy(
                            text = notele.description,
                            isHintVisible = false
                        )
                        _priorityColor.intValue = notele.color
                    }
                }
            }
        }
    }

    /*Recompone los componente*/
    fun onEvent(event : AddEditNotelesEvent){
        when(event) {
            is AddEditNotelesEvent.Color -> {
                _priorityColor.intValue = event.value
            }
            is AddEditNotelesEvent.Description -> {
                _editDescription.value = editDescription.value.copy(
                    text = event.value
                )
            }
            is AddEditNotelesEvent.DescriptionChange -> {
                _editDescription.value = editDescription.value.copy(
                    isHintVisible = event.valueChange.isFocused != _editDescription.value.text.isBlank()
                )
            }
            is AddEditNotelesEvent.Title -> {
                _editTitle.value = editTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditNotelesEvent.TitleChange -> {
                _editTitle.value = editTitle.value.copy(
                    isHintVisible = event.valueChange.isFocused != _editTitle.value.text.isBlank()
                )
            }
            is AddEditNotelesEvent.OnSaveItem -> {
                viewModelScope.launch {
                    try {
                        usesCases.addNotele(
                            NoteleModel(
                                title = editTitle.value.text,
                                description = editDescription.value.text,
                                color = priorityColor.value,
                                time = System.currentTimeMillis(),
                                idNotele = currendId
                            )
                        )
                        Log.e("Item: ", "${currendId}")
                        _stateEvent.emit(UiEvent.SaveNotele)
                    }catch (e: Exception){
                        _stateEvent.emit(UiEvent.ShowSnackBar(
                            e.message ?: "Ingrese los datos"
                        ))
                    }
                }
            }
        }
    }


    sealed class UiEvent {
        data class ShowSnackBar(val message: String): UiEvent()
        data object SaveNotele : UiEvent()
    }
}

data class StateEditTextField(
    val text : String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)