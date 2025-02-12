package com.example.notele.domain.model

import com.example.notele.domain.usecases.AddNotele
import com.example.notele.domain.usecases.DeleteNotele
import com.example.notele.domain.usecases.GetIdNote
import com.example.notele.domain.GetMapUseCase

/*Group uses case for a architecture clean*/
/*make configuration and inject easier*/
/*this allows what viewmodel receive a object*/
data class ModelUsesCases (
    val getMapUseCase : GetMapUseCase, // get list
    val deleteNotele: DeleteNotele, // delete note
    val addNotele: AddNotele, // add new note
    val getIdNote: GetIdNote // get note for ID
)