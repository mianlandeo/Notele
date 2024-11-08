package com.example.notele.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notele.db.model.NoteleModel
import com.example.notele.repository.NoteleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteleRepository: NoteleRepository
): ViewModel(){

    private val _stateTitle = MutableStateFlow("")
    val stateTitle : StateFlow<String> = _stateTitle

    private val _stateDescription = MutableStateFlow("")
    val stateDescription : StateFlow<String> = _stateDescription

    /*private val _listNote = MutableStateFlow<List<NoteleModel>>(emptyList())
    val listNote: StateFlow<List<NoteleModel>> get() = _listNote*/

    val listNote: Flow<List<NoteleModel>> = noteleRepository.getAllNotes()

    fun getListNotele(){
        viewModelScope.launch {

        }
    }
}
