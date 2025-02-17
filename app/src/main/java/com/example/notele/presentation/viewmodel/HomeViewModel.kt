package com.example.notele.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notele.data.db.model.NoteleModel
import com.example.notele.domain.model.ModelUsesCases
import com.example.notele.domain.model.NoteleOrder
import com.example.notele.domain.model.NoteleType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: ModelUsesCases
): ViewModel() {

    /*current status of the List*/
    private val _state = mutableStateOf(NoteleState())
    val state : State<NoteleState> = _state

    /*store the value delete */
    private var recentlyDeleteNotele : NoteleModel? = null
    private var getNoteleJob : Job? = null

    init {
        /*Initialize list in date Descending*/
        getListNote(NoteleOrder.Date(NoteleType.Descending))
    }

    fun getEvent(noteleEvent: NoteleEvent){
        when(noteleEvent){
            is NoteleEvent.Order -> {
                if (state.value.noteOrder == noteleEvent.noteOrder
                    && state.value.noteOrder.noteleType == noteleEvent.noteOrder.noteleType){
                    return
                }
                getListNote(noteleEvent.noteOrder)
            }
            is NoteleEvent.Delete -> {
                viewModelScope.launch {
                    useCases.deleteNotele(noteleEvent.notele)
                    recentlyDeleteNotele = noteleEvent.notele
                    Log.e("Eliminado:", "$recentlyDeleteNotele")
                }
            }
            is NoteleEvent.RestoreNote -> {
                viewModelScope.launch {
                    useCases.addNotele(recentlyDeleteNotele ?: return@launch)
                }
            }
            is NoteleEvent.ToggleOrderSection -> {
                _state.value = _state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getListNote(noteleOrder: NoteleOrder){
        getNoteleJob?.cancel()
        getNoteleJob = useCases.sortNotesUseCase(noteleOrder)
            .onEach { notes -> //Almacena en cache
                _state.value = _state.value.copy(
                    noteList = notes,
                    noteOrder = noteleOrder
                )
            }.launchIn(viewModelScope)
    }
}

data class NoteleState(
    val noteList : List<NoteleModel> = emptyList(),
    val noteOrder : NoteleOrder = NoteleOrder.Date(NoteleType.Descending), //Orden predeterminado -> Fecha-descendente
    val isOrderSectionVisible: Boolean = false, //Orden de la visualizacion, iniciando como false
)
