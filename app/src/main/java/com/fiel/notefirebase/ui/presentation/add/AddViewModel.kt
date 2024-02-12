package com.fiel.notefirebase.ui.presentation.add

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
class AddViewModel @Inject constructor(private val useCase: NoteUseCase): ViewModel() {
    var titulo by mutableStateOf("")
    var contenido by mutableStateOf("")

    var addResponse by mutableStateOf<DataResponse<Boolean>?>(null)


    fun insertNote()=viewModelScope.launch {
        addResponse=DataResponse.Loading
        addResponse=useCase.insertNote(Note(titulo=titulo, contenido = contenido))
    }
}