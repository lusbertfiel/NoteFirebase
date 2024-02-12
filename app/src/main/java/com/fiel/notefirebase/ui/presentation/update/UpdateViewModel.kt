package com.fiel.notefirebase.ui.presentation.update

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiel.notefirebase.ui.domain.model.DataResponse
import com.fiel.notefirebase.ui.domain.model.Note
import com.fiel.notefirebase.ui.domain.usecase.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCase: NoteUseCase
):ViewModel() {
    val data=savedStateHandle.get<String>("nota")
    var note=Note.fromJson(data!!)
    var titulo by mutableStateOf("")
    var contenido by mutableStateOf("")
    var updateResponse by mutableStateOf<DataResponse<Boolean>?>(null)

    init {
        if (note!=null){
            titulo=note.titulo
            contenido=note.contenido
        }
    }
    fun updateNote()=viewModelScope.launch {
        updateResponse=DataResponse.Loading
        updateResponse=useCase.updateNote(Note(id = note.id,titulo=titulo,contenido=contenido))
    }
}