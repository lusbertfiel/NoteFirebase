package com.fiel.notefirebase.ui.domain.usecase

import com.fiel.notefirebase.ui.domain.model.Note
import com.fiel.notefirebase.ui.domain.repository.Repository

class DeleteNote (private val repository: Repository){
    suspend operator fun invoke(note: Note)=repository.deleteNote(note)
}