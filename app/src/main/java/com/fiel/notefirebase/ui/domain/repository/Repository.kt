package com.fiel.notefirebase.ui.domain.repository

import com.fiel.notefirebase.ui.domain.model.DataResponse
import com.fiel.notefirebase.ui.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun insertNote(note: Note):DataResponse<Boolean>
    suspend fun deleteNote(note: Note):DataResponse<Boolean>
    suspend fun updateNote(note: Note):DataResponse<Boolean>

    fun getNotes(): Flow<DataResponse<List<Note>>>
}