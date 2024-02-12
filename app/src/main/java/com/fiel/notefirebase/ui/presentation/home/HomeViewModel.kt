package com.fiel.notefirebase.ui.presentation.home


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiel.notefirebase.ui.domain.model.DataResponse
import com.fiel.notefirebase.ui.domain.model.Note
import com.fiel.notefirebase.ui.domain.usecase.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: NoteUseCase):ViewModel() {

    var notesReponse by mutableStateOf<DataResponse<List<Note>>?>(null)
    var deleteResponse by mutableStateOf<DataResponse<Boolean>?>(null)
    init {
        getNotes()
    }

    fun getNotes()=viewModelScope.launch {
        notesReponse=DataResponse.Loading
        useCase.getNotes().collect(){
            notesReponse=it
        }
    }
    fun deleteNote(note: Note)=viewModelScope.launch{
        deleteResponse=DataResponse.Loading
        deleteResponse=useCase.deleteNote(note)
    }
}