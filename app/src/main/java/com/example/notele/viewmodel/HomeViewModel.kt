package com.example.notele.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notele.db.model.NoteleModel
import com.example.notele.usecases.NoteleEvent
import com.example.notele.usecases.model.ModelUseCases
import com.example.notele.usecases.model.NoteleOrder
import com.example.notele.usecases.model.NoteleType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: ModelUseCases
): ViewModel(){

    /*Estado actual del usuario*/
    private val _state = mutableStateOf(NoteleState())
    val state : State<NoteleState> = _state

    //Variable que almacena el eliminado temporalmente
    private var recentlyDeleteNotele : NoteleModel? = null
    private var getNoteleJob : Job? = null

    init {
        //Iniciamos y obtenemos la fecha de creacion de cada nota
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
        getNoteleJob = useCases.getAllList(noteleOrder)
            .onEach { notes -> //Almacena en cache
                _state.value = _state.value.copy(
                    noteList = notes,
                    noteOrder = noteleOrder
                )
            }
            .launchIn(viewModelScope)
    }

}
data class NoteleState(
    val noteList : List<NoteleModel> = emptyList(),
    val noteOrder : NoteleOrder = NoteleOrder.Date(NoteleType.Descending), //Orden predeterminado de manera decendente
    val isOrderSectionVisible: Boolean = false, //Orden de la visualizacion, iniciando como false
)
